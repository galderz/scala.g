package scalag.futures

import scala.concurrent.ExecutionContext.Implicits.global
import scala.collection.mutable
import scala.concurrent.Future

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object Futures extends App {

  val memory = mutable.Queue[String]("a", "b", "c")

  def readFromMemory(): Future[String] = Future {
      memory.dequeue()
  }

  val packet = readFromMemory()

  packet.onSuccess {
    case bs =>
  }

  packet.onSuccess {
    case bs =>
  }

  // Only two elements left, instead of 3 or 1.
  // That's because there's only one future, which has been consumed by
  // onSuccess, no matter how many times is called
  println(memory.size)

}
