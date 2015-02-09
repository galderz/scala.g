package scalazg.learning.day6

import g.scalaz.S

import scalaz._
import Scalaz._
import scalazg._

/**
 * http://eed3si9n.com/learning-scalaz/Reader.html
 */
object MonadReader extends App {

  {
    // Both (*2) and (+10) get applied to the number 3 in this case. return (a+b) does as well,
    // but it ignores it and always presents a+b as the result. For this reason,
    // the function monad is also called the reader monad. All the functions read from a common source.
    val addStuff: Int => Int = for {
      a <- (_: Int) * 2
      b <- (_: Int) + 10
    } yield a + b
    S $ addStuff(3)
  }

}
