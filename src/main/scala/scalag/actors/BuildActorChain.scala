package scalag.actors

import actors.Actor
import actors.Actor.react
import actors.Actor.actor
import actors.Actor.sender
import actors.Actor.receive

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object BuildActorChain {

  def main(args : Array[String]) {
    // val numActors = args(0).toInt
    val numActors = 5 // sample value
    val start = System.currentTimeMillis
    // Option 1, non-recursive
    buildChain(numActors, null) ! 'Die
    // Option 2, recursive
    // waitFor(numActors, null)
    receive {
      case 'Ack =>
        println("Final ack")
        val end = System.currentTimeMillis
        println("Took " + (end - start) + " ms")
    }
  }

  def buildChain(size: Int, next: Actor): Actor = {
    val a = actor {
      react {
        case 'Die =>
          println("Die...")
          val from = sender
          if (next != null) {
            next ! 'Die
            react {
              case 'Ack =>
                println("Ack...")
                from ! 'Ack
            }
          } else from ! 'Ack
      }
    }
    if (size > 0) buildChain(size - 1, a)
    else a
  }

  // TODO: How to combine?
  def waitFor(n: Int, next: Actor) {
    if (n > 0) {
      react {
        case 'Die =>
          val from = sender
          if (next != null) {
            next ! 'Die
            react {
              case 'Ack => from ! 'Ack; waitFor(n - 1, next)
            }
          } else {
            from ! 'Ack; waitFor(n - 1, next)
          }
      }
    }
  }

}
