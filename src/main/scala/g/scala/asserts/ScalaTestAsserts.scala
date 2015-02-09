package g.scala.asserts

import org.scalatest.Matchers._
import org.junit.Assert
import org.hamcrest.Matchers.instanceOf

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ScalaTestAsserts extends App {

  // List(1,2,3) shouldBe a [List[_]]
  // "" shouldBe a [List[_]] // recommended

  Assert.assertThat(new Exception, instanceOf(classOf[ArrayIndexOutOfBoundsException]))

}
