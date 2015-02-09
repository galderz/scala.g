package g.scala.futures

import scala.concurrent._
import scala.util.{Failure, Success}
import ExecutionContext.Implicits.global

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object Promises {

   def main(args : Array[String]) {
//      // either give the type as a type parameter to the factory method:
//      val taxcut = Promise[TaxCut]()
//      val taxcutF: Future[TaxCut] = taxcut.future
//      taxcut.success(TaxCut(20))

      val taxCutF: Future[TaxCut] = Government.redeemCampaignPledge()
      println("Now that they're elected, let's see if they remember their promises...")
      taxCutF.onComplete {
         case Success(TaxCut(reduction)) =>
            println(s"A miracle! They really cut our taxes by $reduction percentage points!")
         case Failure(ex) =>
            println(s"They broke their promises! Again! Because of a ${ex.getMessage}")
      }

      Thread.sleep(5000)
   }

   case class TaxCut(reduction: Int)

   object Government {
      def redeemCampaignPledge(): Future[TaxCut] = {
         val p = Promise[TaxCut]()
         future {
            println("Starting the new legislative period.")
            Thread.sleep(2000)
            p.success(TaxCut(20))
            println("We reduced the taxes! You must reelect us!!!!1111")
         }
         p.future
      }
   }

   case class LameExcuse(msg: String) extends Exception(msg)

   object LameGovernment {
      def redeemCampaignPledge(): Future[TaxCut] = {
         val p = Promise[TaxCut]()
         future {
            println("Starting the new legislative period.")
            Thread.sleep(2000)
            p.failure(LameExcuse("global economy crisis"))
            println("We didn't fulfill our promises, but surely they'll understand.")
         }
         p.future
      }
   }
}
