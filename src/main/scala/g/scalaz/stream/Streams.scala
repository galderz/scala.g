package g.scalaz.stream

import scalaz.{stream, \/}
import scalaz.\/._
import scalaz.concurrent.Task

object Streams extends App {

  def asyncReadInt(callback: Throwable \/ Int => Unit): Unit = {
    // imagine an asynchronous task which eventually produces an `Int`
    try {
      Thread.sleep(50)
      val result = (math.random * 100).toInt
      callback(right(result))
    } catch { case t: Throwable => callback(left(t)) }
  }

  /* We can wrap this in `Task`, which forms a `Monad` */
  val intTask: Task[Int] = Task.async(asyncReadInt)

  val asyncInts: stream.Process[Task, Int] = scalaz.stream.Process.eval(intTask).repeat

  private val newTask = for {
    intNum <- asyncInts.toTask
  } yield {
    print(intNum)
  }

  newTask.run

  def x(i: Int): Either[Throwable, Int] =
    if (i == 0) Left(new Exception(i.toString))
    else Right(i)

//  asyncReadInt((\/.fromEither(x(1))) => ())


//  val oddsGt10: Process[Task,String] =
//    asyncInts.take(25)
//      .filter(_ > 10)
//      .filter(_ % 2 != 0)
//      .map(_.toString)
//
//  val r: Task[collection.immutable.IndexedSeq[String]] = oddsGt10.runLog
//
//  val ok1: Boolean = r.run.forall(i => i.toInt > 10)
//
//  println(ok1)


//  val ones: Process[Task,Int] = Process.constant(1).repeat
//  // ones.toTask.map(println(_)).run
//
//  private val process = for {
//    one <- ones.toTask
//  } yield {
//    println(one)
//  }
//
//  process.run
//
////  //ones.run
////  ones.toTask.run
  Thread.sleep(10000)

}
