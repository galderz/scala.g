package g.scala.regex

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object RegularExpressions {

   def main(args : Array[String]) {
      println("(.*.jar)".r.findFirstIn("cache").isDefined)
      println("(.*.jar)".r.findFirstIn("play21-helloworld_2.10-1.0-SNAPSHOT.jar").isDefined)

      println

      val pattern = "(org.infinispan.(?!test).*)".r
      println(pattern.findFirstIn("org.infinispan.util").isDefined)
      println(pattern.findFirstIn("org.infinispan.test").isDefined)
      println(pattern.findFirstIn("org.infinispan.marshalling").isDefined)

      println("[0-9_]*".r.findPrefixOf("4aaa"))
   }

}
