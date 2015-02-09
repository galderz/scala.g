package g.scala.typealias.typed

import g.scala.typealias.MyValue

/**
 * By defining some type aliases in your base package,
 * you give users your dependencies for free :)
 */
object Typed extends App {

   val v = MyValue(
     "any",
     ContentType.`application/json`,
     new DateTime()
   )

 }
