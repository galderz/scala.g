package scalazg.learning.day7

import g.scalaz.S

import scalaz._
import Scalaz._
import scalazg._

/**
 * http://eed3si9n.com/learning-scalaz/Either.html
 */
object MonadEither extends App {

  {
    S $ ("event 1 failed!".left ||| "retry event 1 ok".right) // ||| is orElse
    S $ ("event 1 ok".right map {_ + "!"})

    // For left value, we can call swap method or it’s symbolic alias unary_~:

    S $ "event 1 ok".right | "something bad" // | is getOrElse
    S $ ("event 1 bad".left getOrElse "something good") // | is getOrElse

    S $ "event 1 ok".right.isRight
    S $ "event 1 ok".right.isLeft

    val result = for {
      e1 <- "event ok".right
      e2 <- "event 2 failed!".left[String]
      e3 <- "event 3 failed!".left[String]
    } yield e1 |+| e2 |+| e3
    S $ result

    // The Either type in Scala standard library is not a monad on its own,
    // which means it does not implement flatMap method with or without Scalaz:
    S $ ("boom".left[Int] >>= { x => (x + 1).right })

    // We know Either[A, B] from the standard library, but Scalaz 7
    // implements its own Either equivalent named \/:
    S $ 1.right[String]     // scalaz.\/[String,Int] = \/-(1)
    S $ "error".left[Int]   // scalaz.\/[String,Int] = -\/(error)
  }

}
