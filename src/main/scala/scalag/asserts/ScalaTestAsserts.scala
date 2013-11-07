package scalag.asserts

import org.scalatest.Matchers._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ScalaTestAsserts extends App {

  List(1,2,3) shouldBe a [List[_]]
  // "" shouldBe a [List[_]] // recommended

}
