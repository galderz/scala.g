package scalag.typealias.untyped

import scalag.typealias.MyValue
import spray.http.ContentType
import org.joda.time.DateTime

/**
 * When your API refers to external APIs,
 * you force users to always have to import those types :(
 */
object UnTyped {

  val v = MyValue(
    "any",
    ContentType.`application/json`,
    new DateTime()
  )

}
