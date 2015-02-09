package g.scala.futures

import scala.concurrent.{ExecutionContext, Promise, Await, Future}
import scala.async.Async._
import scala.concurrent.duration._
//import scala.concurrent.ExecutionContext.Implicits.global

/**
 * @author Galder Zamarreño
 */
object CustomExecutorContext extends App {

  implicit val executionContext = SameThreadExecutionContext.from()

  def always[T](value: T): Future[T] = Future.successful(value)
  //def always[T](value: T): Future[T] = Promise[T]().future

  val f = always(517).map {x => assert(x == 517); x}
  assert(Await.result(f, 0 nanos) == 517)

  // TODO: Maybe add a handler instance conversion? Like in vert.x?

}

object SameThreadExecutionContext {

  def from(): ExecutionContext = new SameThreadExecutionContextImpl

  class SameThreadExecutionContextImpl extends ExecutionContext {
    override def reportFailure(t: Throwable): Unit = t.printStackTrace(System.err)
    override def execute(runnable: Runnable): Unit = runnable.run()
  }

}
