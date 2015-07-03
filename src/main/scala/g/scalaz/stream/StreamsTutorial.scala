package g.scalaz.stream

import scalaz.concurrent.Task
import scalaz._
import Scalaz._
import scalaz.\/._
import scalaz.stream.Process

object StreamsTutorial extends App {

  val f: Task[Unit] = Task.async { a =>
    println("scalaz")
  }

  val repeatedF: Process[Task, Unit] = Process.repeatEval(f)

  repeatedF.run

  val p2: Process[Task, Int] = Process.range(0,10)
  val mapResult = p2.map(x => "the value is " + x).runLog.run
  val filterResult = p2.filter(x => x % 2 == 0).runLog.run

  println(mapResult)
  println(filterResult)

  //

}
