package scalag.actors

import actors.Actor
import actors.scheduler.DaemonScheduler

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object DaemonActor {

  def main (args: Array[String]) {
    val d = (new MyDaemon).start()
    println (d !? 41)
  }

}

class MyDaemon extends Actor {
  override def scheduler = DaemonScheduler

  def act() {
    loop {
      react {case num: Int => reply(num + 1)}
    }
  }
}

