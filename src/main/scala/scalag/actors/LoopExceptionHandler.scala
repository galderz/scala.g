package scalag.actors

import actors.Actor
import actors.Actor.react

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object LoopExceptionHandler {

  def main(args: Array[String]) {
    LastMessageRecord.start()
    LastMessageRecord ! 'hello
    Thread.sleep(1000) // Wait a little until message has been consumed
    println(LastMessageRecord.getState)
    LastMessageRecord ! 'hi
    LastMessageRecord ! 'stop
    Thread.sleep(1000) // Wait a little until message has been consumed
    println(LastMessageRecord.getState)
  }

}

object LastMessageRecord extends Actor {

  def act() {
    var lastMsg: Option[Symbol] = None
    loopWhile (lastMsg.isEmpty || lastMsg.get != 'stop) {
      react {
        case 'hello => throw new Exception("Error")
        case any: Symbol =>
          println("Your message: " + any)
          lastMsg = Some(any)
      }
    }
  }

  override def exceptionHandler: PartialFunction[Exception, Unit] = {
    case e: Exception => println(e.getMessage)
  }

}