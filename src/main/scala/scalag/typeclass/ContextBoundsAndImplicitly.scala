package scalag.typeclass

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ContextBoundsAndImplicitly {

  // Implicit parameters that look like this
  def sum[T](l: List[T])(implicit integral: Integral[T]): T = ???

  // Can also be written like this
  def sum2[T: Integral](l: List[T]): T = {
    // You can always get a reference to any implicit value or parameter
    // using the implicitly function
    val integral = implicitly[Integral[T]]
    ???
  }



}
