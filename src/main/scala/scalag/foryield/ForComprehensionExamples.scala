package scalag.foryield

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ForComprehensionExamples {

   def main(args : Array[String]) {
      val x = "galder"
      (for {
         dm <- Some(x)
         location <- Some(dm)
      } yield {
         location.toString
      }).getOrElse {
         null
      }
   }

}
