package scalag.extractor

/**
 * Extractor object example taken from:
 * http://www.scala-lang.org/node/112
 */
object TwiceExtractor {

   def main(args : Array[String]) {
      val x = Twice(21) // calls apply(2 * 21) = 42
      x match {
         case Twice(n) => Console.println(n)
      } // prints 21 since it calls unapply(42)
   }

}

object Twice {
   def apply(x: Int): Int = x * 2
   def unapply(z: Int): Option[Int] = {
      println("unapply: " + z)
      if (z%2 == 0) Some(z/2)
      else None
   }
}