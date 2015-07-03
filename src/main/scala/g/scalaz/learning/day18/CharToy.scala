package scalazg.learning.day18

import scalaz._
import Scalaz._

/**
 * @author Galder Zamarreño
 */
sealed trait CharToy[+N]

object CharToy {
  case class CharOutput[N](a: Char, next: N) extends CharToy[N]
  case class CharBell[N](next: N) extends CharToy[N]
  case class CharDone() extends CharToy[Nothing]

  // With free monad
  implicit val charToFunctor: Functor[CharToy] = new Functor[CharToy] {
    def map[A, B](fa: CharToy[A])(f: A => B): CharToy[B] = fa match {
      case o: CharOutput[A] => CharOutput(o.a, f(o.next))
      case b: CharBell[A]   => CharBell(f(b.next))
      case CharDone()       => CharDone()
    }
  }

// Not compiling...
//
//  private def liftF[F[+_]: Functor, R](command: F[R]): Free[F, R] =
//    Free.Suspend[F, R](Functor[F].map(command) { Free.Return[F, R](_) })

// Not compiling...
//
//  def output(a: Char): Free[CharToy, Unit] = liftF[CharToy, Unit](CharOutput(a, ()))
//  def bell: Free[CharToy, Unit] = liftF[CharToy, Unit](CharBell(()))
//  def done: Free[CharToy, Unit] = liftF[CharToy, Unit](CharDone())
//  def pointed[A](a: A) = Free.Return[CharToy, A](a)

  def showProgram[R: Show](p: Free[CharToy, R]): String =
    p.resume.fold({
      case CharOutput(a, next) =>
        "output " + Show[Char].shows(a) + "\n" + showProgram(next)
      case CharBell(next) =>
        "bell " + "\n" + showProgram(next)
      case CharDone() =>
        "done\n"
    },
    { r: R => "return " + Show[R].shows(r) + "\n" })

  // Without free monad
  //  def output[N](a: Char, next: N): CharToy[N] = CharOutput(a, next)
  //  def bell[N](next: N): CharToy[N] = CharBell(next)
  //  def done: CharToy[Nothing] = CharDone()
}

