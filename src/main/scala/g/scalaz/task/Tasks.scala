package g.scalaz.task

import com.ning.http.client.{Response, AsyncCompletionHandler, AsyncHttpClient}

import scalaz._
import Scalaz._
import scalaz.concurrent.Task

object Tasks extends App {

  val task = Task { println("foo") }
  task.run
  task.attemptRun

  val task1: Task[Int] = Task.now(1)
  println(task1.run)

  val task2: Task[Int] = Task.fail(new Exception("example")) or Task.now(1)
  println(task2.run)

  try {
    val task3 = Task.fail(new Exception("boom")).run
    throw new AssertionError("Expected exception")
  } catch {
    case e: Exception => // expected
  }

  val asyncHttp = new AsyncHttpClient
  asyncHttp.prepareGet("http://google.com").execute(new AsyncCompletionHandler[Response] {
    override def onCompleted(response: Response): Response = {
      println(response.getResponseBody)
      response
    }
  })

  def get(s: String): Task[Response] =
    Task.async[Response](k => asyncHttp.prepareGet(s).execute(toHandler(k)))

  trait MyResults

  def processData: Response => Task[MyResults] = {
    // Do something...
    null
  }

  def toHandler(k: (Throwable \/ Response) => Unit) = new AsyncCompletionHandler[Unit] {
    override def onCompleted(r: Response): Unit = k(\/-(r))
    override def onThrowable(t: Throwable): Unit = k(-\/(t))
  }

  val myTask: Task[MyResults] = get("http://google.com").flatMap(processData)//...etc
  // myTask.run..
  asyncHttp.close()

  val tasks = (1 |->  5).map(n => Task { Thread.sleep(100); n })
  println(Task.gatherUnordered(tasks).run)

//  val task4 = Task { Thread.sleep(1000); "one" }.timed(100)
//  task4.run

  val task5 = Task(10 / 0)
  val task6 = task5.handleWith {
    case e: ArithmeticException => Task.now(0)
  }
  println(task6.run)

  import java.util.concurrent.{ExecutorService,Executors}
  type Delegated[A] = Kleisli[Task, ExecutorService, A]

  def delegate: Delegated[ExecutorService] = Kleisli(e => Task.now(e))
  implicit def delegateTaskToPool[A](a: Task[A]): Delegated[A] = Kleisli(x => a)

  val exe = for {
    p <- delegate
    b <- Task("x")(p)
    c <- Task("y")(p)
    } yield c

  val pool = Executors.newFixedThreadPool(1)
  val res0 = exe.run(pool)
  println(res0.run)
  pool.shutdownNow()
}
