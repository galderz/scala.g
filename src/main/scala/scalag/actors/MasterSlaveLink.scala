package scalag.actors

import actors.{Exit, Actor}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object MasterSlaveLink {

  def main(args: Array[String]) {
    Slave.start()
    Thread.sleep(1000) // Wait a little until message has been consumed
    println(Slave.getState)
    Master.start()
    Thread.sleep(1000) // Wait a little until message has been consumed
    println(Master.getState)
    println(Slave.getState)
  }

}

object Master extends Actor {

  def act() {
    Slave ! 'doWork
    react {
      case 'done =>
        throw new Exception("Master crashed")
    }
  }

}

object Slave extends Actor {

  def act() {
    trapExit = true
    link(Master)
    loop {
      react {
        case 'doWork =>
          println("Done")
          reply('done)
        case Exit(from, reason) if from == Master =>
          println("Slave actor terminated because of " + reason)
          exit() // Required since we're in a loop...
      }
    }
  }

}