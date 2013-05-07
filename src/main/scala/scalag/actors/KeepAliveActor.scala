package scalag.actors

import actors.Actor.actor
import actors.Actor.self
import actors.Actor.link
import actors.Actor.loop
import actors.Actor.react

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object KeepAliveActor {

  def main (args: Array[String]) {
    val crasher = actor {
      println("I'm (re-)born")
      var cnt = 0
      loop {
        cnt += 1
        react {
          case 'request =>
            println("I try to service a request")
            if (cnt % 2 == 0) {
              println("sometimes I crash...")
              throw new Exception
            }
          case 'stop =>
            exit()
        } }
    }
    val client = actor {
      react {
        case 'start =>
          for (_ <- 1 to 6) { crasher ! 'request }
          crasher ! 'stop
      }
    }
    val a = actor {
      self.trapExit = true
      link(crasher)
      client ! 'start
      // keepAlive(crasher) // Where is keepAlive??
    }
  }

}
