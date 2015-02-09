package g.scala.error

import java.util.StringTokenizer

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object EitherExample {

   case class FailResult(reason: String)

   def parse(input:String) : Either[FailResult, String] = {
      val r = new StringTokenizer(input)
      if (r.countTokens() == 1) {
         Right(r.nextToken())
      } else {
         Left(FailResult("Could not parse string: " + input))
      }
   }

   def main(args : Array[String]) {
      parse("galder").fold(
         error => println("Error!"),
         success => println("Success!")
      )
   }

}
