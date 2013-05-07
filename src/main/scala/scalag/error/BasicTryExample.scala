package scalag.error

import util.Try

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object BasicTryExample {

   def main(args : Array[String]) {
      val sumTry = for {
         int1 <- Try(Integer.parseInt("1"))
         int2 <- Try(Integer.parseInt("2"))
      } yield {
         int1 + int2
      }

      println(sumTry)

      val errorSumTry = for {
         int1 <- Try(Integer.parseInt("a"))
         int2 <- Try(Integer.parseInt("2"))
      } yield {
         int1 + int2
      }

      println(errorSumTry)
   }

}
