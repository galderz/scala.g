package scalag.regex

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object RegularExpressions {

   def main(args : Array[String]) {
      println("(.*.jar)".r.findFirstIn("cache").isDefined)
      println("(.*.jar)".r.findFirstIn("play21-helloworld_2.10-1.0-SNAPSHOT.jar").isDefined)
   }

}
