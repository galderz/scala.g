package scalag.typealias.typed

import scalag.typealias.MyValue

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
