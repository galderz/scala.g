package g.scala.demux

import scala.collection.mutable.ListBuffer

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object TruthTableScala extends App {

//  def printTruthTable(i: Int) {
//    val rows = Math.pow(2, i)
//    var l = new ListBuffer[Boolean]
//    for (a <- 0 until rows.toInt)
//      l  += false
//
//    def outer(i: Int, l: List[Boolean]) {
//      l match {
//        case Nil =>
//        case x::xs =>
//          // println(x)
//          println(l.size)
//          for (i <- 1 until i) {
//            println(Math.pow(2, ys.size))
//          }
//
//          def inner(l2: List[Boolean]) {
//            l2 match {
//              case Nil =>
//              case y::ys =>
//                // print((l.size / Math.pow(2, ys.size).toInt) % 2 + " ")
//                inner(ys)
//            }
//          }
//
//          inner(xs)
//          println
//          outer(i, xs)
//      }
//    }
//
//    outer(i, l.toList)
//  }
//
//  printTruthTable(2)

  def outer(in: Boolean, l: List[Boolean], c: List[Boolean]) {
    l match {
      case Nil =>
      case x::xs =>
        val result =
          c.foldLeft(in) { (a, b) =>
            a && b
          }

        println(l.size)
        println(result)
        outer(in, xs, c)

        // println(List(false, false).foldLeft(true)(_ & _))

    }
  }

  val l = List(false, false, false, false)
  outer(true, l, List(false, false))






}
