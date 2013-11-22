package scalag.varia

import scala.math._
import scala.concurrent.Future
import scala.util.{Failure, Success, Try}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object Varia extends App {

  def randomBelow(i: Int) = (random * i).toInt

  {
    object TryFactory {
      def apply[T](f: Future[T]): Future[Try[T]] = {
        // f.map(x => Try(x)) <-- does not work because it doesn't map the failure
        f.map(s => Success(s)) recover { case t => Failure(t) }
      }
    }

  }

  {
//    val l = List(true, true, false, true, true)
//    val x = false
//    for {
//      e <- l
//      if x == e
//    } yield {
//      println(e)
//    }
  }

  {
//    var i = 0
//    val l = List.fill(300) {
//      i = i + 1
//      if (i % 100 < 5)
//        false
//      else
//        true
//    }
//    println(i)
//    println(l)
//    println(l.count(b => b))
//    println(l.count(b => !b))
  }

  {
//    var i = 0
//    val l = List.fill(300) {
//      i = i + 1
//      if (i % 100 == 0)
//        false
//      else
//        true
//    }
//    println(i)
//    println(l)
//    println(l.count(b => b))
//    println(l.count(b => !b))
  }

  {
    //  private def move(start: Int, size: Int): Int = {
    //    val mv = randomBelow(2)
    //    mv match {
    //      case 0 => if (start == 0) size - 1 else start - 1
    //      case 1 => if (start == size - 1) 0 else start + 1
    //    }
    //  }
    //
    //  val start = 0
    //  println(s"Start = $start")
    //  private val move1 = move(start, 8)
    //  println(move1)
    //  private val move2 = move(move1, 8)
    //  println(move2)
    //  private val move3 = move(move2, 8)
    //  println(move3)
    //  private val move4 = move(move3, 8)
    //  println(move4)
  }

  {
    //  val col = 0
    //
    //  7 -> 6
    //  6 -> 5
    //  5 -> 4
    //  4 -> 3
    //  2 -> 2
    //  2 -> 1
    //  1 -> 0
    //  0 -> 7
    //
    //  private val moveInCol = randomBelow(2)
    //  println(moveInCol)
    //  moveInCol match {
    //    case 0 => if ()
    //  }
    //
    //  println(randomBelow(2))
    //  println(randomBelow(2))
    //  println(randomBelow(2))
  }

}
