package scalag.actors

import actors.Actor
import actors.Actor.react

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ExceptionHandler {

  def main(args : Array[String]) {
    LastMessageRecord.start()
    LastMessageRecord ! 'Hello
  }

}

object Exceptionable extends Actor {

  def act() {
    react {
      case 'Hello => throw new Exception("Error")
    }
  }

  override def exceptionHandler: PartialFunction[Exception, Unit] = {
    case e: Exception => println(e.getMessage)
  }

}