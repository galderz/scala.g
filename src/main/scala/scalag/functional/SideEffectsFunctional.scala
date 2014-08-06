package scalag.functional

/**
 * From "Composable application architecture with reasonably priced monads" talk:
 * http://www.parleys.com/play/53a7d2c3e4b0543940d9e538/chapter7/about
 */
object SideEffectsFunctional extends App {

  sealed trait Interact[A]
  case class Ask(prompt: String) extends Interact[String]
  case class Tell(msg: String) extends Interact[Unit]

  trait Monad[M[_]] {
    def pure[A](a: A): M[A]
    def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B]
  }

  object Monad {
    def apply[F[_]:Monad]: Monad[F] = implicitly[Monad[F]]
  }

  sealed trait ~>[F[_],G[_]] { self =>
    def apply[A](f: F[A]): G[A]
  }

  sealed trait Free[F[_], A] {
    def flatMap[B](f: A => Free[F,B]): Free[F,B] =
      this match {
        case Return(a) => f(a)
        case Bind(fx, g) =>
          Bind(fx, g andThen (_ flatMap f)) // Ignore IDE error
      }

    def map[B](f: A => B): Free[F,B] =
      flatMap(a => Return(f(a)))

    def foldMap[G[_]:Monad](f: F ~> G): G[A] =
      this match {
        case Return(a) => Monad[G].pure(a)
        case Bind(fx, g) =>
          Monad[G].flatMap(f(fx)) { a =>
            g(a).foldMap(f) // Ignore IDE error
          }
      }
  }

  case class Return[F[_], A](a: A) extends Free[F, A]
  case class Bind[F[_], I, A](i: F[I], k: I => Free[F, A]) extends Free[F, A]

  implicit def lift[F[_], A](fa: F[A]): Free[F, A] =
    Bind(fa, (a: A) => Return(a)) // Ignore IDE error

  type Id[A] = A

  object Console extends (Interact ~> Id) {
    def apply[A](i: Interact[A]) = i match {
      case Ask(prompt) =>
        println(prompt)
        readLine // Ignore IDE error
      case Tell(msg) =>
        println(msg) // Ignore IDE error
    }
  }

  implicit val identityMonad: Monad[Id] = new Monad[Id] {
    def pure[A](a: A) = a
    def flatMap[A,B](a: A)(f: A => B) = f(a)
  }

  type Tester[A] = Map[String, String] => (List[String], A)

  object TestConsole extends (Interact ~> Tester) {
    def apply[A](i: Interact[A]) = i match {
      case Ask(prompt) => m => (List(), m(prompt)) // Ignore IDE error
      case Tell(msg) => _ => (List(msg), ()) // Ignore IDE error
    }
  }

  implicit val testerMonad = new Monad[Tester] {
    def pure[A](a: A) = _ => (List(), a)
    def flatMap[A,B](t: Tester[A])(f: A => Tester[B]) =
      m => {
        val (o1, a) = t(m)
        val (o2, b) = f(a)(m)
        (o1 ++ o2, b)
      }
  }

  val prg: Free[Interact, Unit] = for {
    first <- Ask("What's your first name?")
    last <- Ask("What's your last name?")
    _ <- Tell(s"Hello $first $last")
  } yield ()

  val m = Map(
    "What's your first name?" -> "Harry",
    "What's your last name?" -> "Potter"
  )

  val v = prg.foldMap(TestConsole).apply(m)
  println(v)

  val v2 = prg.foldMap(Console)
  println(v2)

}
