package scalag.typeclass

import scala.concurrent.Future
import scalag.typealias.typed.ContentType

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object TypeClassSerializer {

  trait Bucket {
    def store[T](key: String, value: Value): Future[Unit] = ???
    def store[T](key: String, value: T)(implicit s: Serializer[T]): Future[Unit] =
      store(key, Value(value))
    def store2[T: Serializer](key: String, value: T): Future[Unit] =
      store(key, Value.apply2(value))
  }

  trait Serializer[T] {
    def serialize(t: T): (String, ContentType)
  }

  // Make sure default implicit conversions have the lowest possible priority
  object Serializer extends LowPriorityDefaultSerializerImplicits{
  }

  private trait LowPriorityDefaultSerializerImplicits {
    implicit def stringSerializer = new Serializer[String] {
      def serialize(t: String): (String, ContentType) = (t, ContentType.`text/plain`)
    }
  }

  case class Value(data: String, contentType: ContentType)

  object Value {
    def apply[T](data: T)(implicit s: Serializer[T]): Value = {
      val (dataAsString, contentType) = s.serialize(data)
      Value(dataAsString, contentType)
    }

    def apply2[T: Serializer](data: T): Value = {
      val (dataAsString, contentType) = implicitly[Serializer[T]].serialize(data)
      Value(dataAsString, contentType)
    }
  }

}
