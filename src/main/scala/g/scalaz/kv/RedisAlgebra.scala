package g.scalaz.kv

import scalaz.{ concurrent, Free, Functor, Monad, syntax }
import concurrent.Task
import Free.{freeMonad => _, _}
import syntax.monad._

// Based on http://engineering.sharethrough.com/blog/2015/01/23/writing-a-redis-client-with-a-free-algebra/
object RedisAlgebra extends App{

  // Describe the set of actions - which are functors

  sealed trait RedisF[+A] {
    def map[B](fn: A => B): RedisF[B]
  }

  object RedisF {
    implicit val redisFunctor: Functor[RedisF] = new Functor[RedisF] {
      def map[A, B](fa: RedisF[A])(f: A => B): RedisF[B] = fa map f
    }
  }

  // and some instances of that trait describing our desired actions

  case class Get[+A](k: String, next: String => A) extends RedisF[A] {
    def map[B](fn: A => B): RedisF[B] = copy(next = next andThen fn)
  }
  case class Set[+A](k: String, value: String, next: String => A) extends RedisF[A] {
    def map[B](fn: A => B): RedisF[B] = copy(next = next andThen fn)
  }
  case class Fail[A](excp: Throwable) extends RedisF[A] {
    def map[B](fn: A => B): RedisF[B] = Fail[B](excp)
  }

  // it's a simple dsl :-), it does getting and setting strings to Redis

  object FreeRedis extends FreeRedis

  trait FreeRedis {

    type RedisFree[A] = Free[RedisF, A]

    def just[A](a: => A): RedisFree[A] =
      Monad[RedisFree].pure(a)

    def noAction[A]: RedisFree[A] =
      liftF(Fail(new Throwable("Action failed"))): RedisFree[A]

    // Helper functions describing our redis commands below

    def set(k: String, v: String): RedisFree[String] =
      liftF(Set(k, v, identity))

    def get(k: String): RedisFree[String] =
      liftF(Get(k, identity))

    def fail[A](t: Throwable): RedisFree[A] =
      liftF(Fail(t))

    implicit val redisFreeMonad: Monad[RedisFree] = new Monad[RedisFree] {
      def point[A](a: => A) = Free.point(a)
      def bind[A,B](action: RedisFree[A])(f: A => RedisFree[B]) = action flatMap f
    }

  }

  // Ensure FreeRedis stuff is in scope...
  import FreeRedis._

  // Make a pure program value
  val program1: RedisFree[String] = set("keyA", "valueB") >> get("keyA")
  println(Interpreters.stepList(program1))

  val program2: RedisFree[String] = set("keyA", "valueB") >> fail(new Throwable("connection died")) >> get("keyA")
  println(Interpreters.stepList(program2))

  // Now run program1
  val res0 = program1.runM(Interpreters.step)
  println(res0)
  // Running this shows:
  // setting key: keyA value = valueB
  // getting key: keyA
  val res1 = res0.run
  println(res1) // prints "I'm the key you got!"

  // Try to run program2
  val res2 = program2.runM(Interpreters.step).attempt.run
  println(res2) // prints "-\/(java.lang.Throwable: connection died)"

  object Interpreters extends FreeRedis {

    // Take a program and turn each action into a string, aggregate those strings in a list
    def stepList[A](program: RedisFree[A], actions: List[String] = Nil): List[String] = program.resume.fold({
      case Set(k, v, next)  => stepList(next("success"), s"setting key: ${k} value = ${v}" :: actions)
      case Get(k, next)     => stepList(next("success"), s"getting key: ${k}" :: actions)
      case Fail(excp)       => ("ERROR!" :: actions).reverse
    }, { a: A => ("End of program" :: actions).reverse })

    // Interpret a step (action) of our program and produce a Task which we can run later
    def step[A](action: RedisF[RedisFree[A]]): Task[RedisFree[A]] = action match {
      case Set(k, v, next) => Task { println(s"setting key: ${k} value = ${v}"); "success" } map { next }
      case Get(k, next)    => Task { println(s"getting key: ${k}"); "I'm the key you got!" } map { next }
      case Fail(excp)      => Task.fail(excp)
    }

  }

}
