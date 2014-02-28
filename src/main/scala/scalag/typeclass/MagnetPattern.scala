package scalag.typeclass

/**
 * Find a way to avoid overloaded methods with the magnet pattern.
 * The three overloaded methods are:
 * `def complete(s: String): Boolean`
 * `def complete(n: Int): Boolean`
 * `def complete(b: Boolean): Boolean`
 *
 * See http://spray.io/blog/2012-12-13-the-magnet-pattern/
 *
 * If all overloads have the same return type there is no need for a type member on the magnet type.
 *
 * @author Galder Zamarreño
 */
object MagnetPattern extends App {

  sealed trait CompletionMagnet {
    type Result
    def apply(): Result
  }

  class Completable {
    def complete(magnet: CompletionMagnet): magnet.Result =
      magnet() // ignore IDE error
  }

  object CompletionMagnet {
    implicit def fromString(s: String) =
      new CompletionMagnet {
        type Result = Boolean
        def apply(): Result = if (s == "success") true else false
      }
    implicit def fromInt(i: Int) =
      new CompletionMagnet {
        type Result = Boolean
        def apply(): Result = if (i > 0) true else false
      }
    implicit def fromBoolean(b: Boolean) =
      new CompletionMagnet {
        type Result = Boolean
        def apply(): Result = b
      }
  }

  println(new Completable().complete("failure"))
  println(new Completable().complete("success"))
  println(new Completable().complete(0))
  println(new Completable().complete(1))
  println(new Completable().complete(false))
  println(new Completable().complete(true))

}
