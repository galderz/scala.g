package scalag.typealias

import spray.http.ContentType
import org.joda.time.DateTime

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
final case class MyValue(
  data: String,
  contentType: ContentType, // <= external type
  lastModified: DateTime // <= external type
)

