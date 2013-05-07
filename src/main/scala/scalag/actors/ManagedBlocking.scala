package scalag.actors

import scala.actors.Actor
import scala.actors.Actor._
import scala.concurrent.ManagedBlocker
import java.util.concurrent.CountDownLatch

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ManagedBlocking {

  class BlockingActor(i: Int, latch: CountDownLatch)
    extends Actor {
    val blocker = new ManagedBlocker {
      def block() = {
        latch.await(); true
      }

      def isReleasable = {
        latch.getCount == 0
      }
    }

    def act() {
      scheduler.managedBlock(blocker)
      println("actor " + i + " done")
    }
  }

  def main(args: Array[String]) {
    val numCores = Runtime.getRuntime().availableProcessors()
    println("available cores: " + numCores)
    val latch = new CountDownLatch(1)
    for (i <- 1 to (numCores * 2))
      new BlockingActor(i, latch).start()

    actor {
      latch.countDown()
    }
  }

}
