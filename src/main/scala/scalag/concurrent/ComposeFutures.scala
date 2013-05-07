package scalag.concurrent

import scala.concurrent._
import ExecutionContext.Implicits.global

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ComposeFutures {

   def main(args : Array[String]) {
      val startTime = System.currentTimeMillis
      val future1 = future(timeTakingIdentityFunction(1))
      val future2 = future(timeTakingIdentityFunction(2))
      val future3 = future(timeTakingIdentityFunction(3))

      val total = for {
         x <- future1
         y <- future2
         z <- future3
      } yield (x + y + z)

      total onSuccess {
         case sum =>
            val elapsedTime = ((System.currentTimeMillis - startTime) / 1000.0)
            println("Sum of 1, 2 and 3 is " + sum + " calculated in " + elapsedTime + " seconds")
      }

      Thread.sleep(5000)
   }

   def timeTakingIdentityFunction(number: Int) = {
      // we sleep for 3 seconds and return number
      Thread.sleep(1000)
      number
   }

}
