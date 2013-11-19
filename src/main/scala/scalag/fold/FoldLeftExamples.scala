package scalag.fold

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object FoldLeftExamples extends App {

  def sum(l: List[Int]): Int = l.foldLeft(0)(_ + _)
  def sum2(l: List[Int]): Int = l.foldLeft(0)((r, c) => r + c)
  println(sum(List(1, 5, 9)))

  def product(l: List[Int]): Int = l.foldLeft(1)(_ * _)
  println(product(List(2, 5, 3)))

  def count(l: List[Any]): Int = l.foldLeft(0)((sum, _) => sum + 1)
  println(count(List(2, 5, 3, 99)))

  def averageSlow(l: List[Double]): Double =
    l.foldLeft(0.0)(_ + _) / l.foldLeft(0)((sum, _) => sum + 1)
  println(averageSlow(List(1.0, 2.0, 3.0)))

  def average(l: List[Double]): Double = {
    l match {
      case Nil => 0.0
      case head :: tail =>
        val result = tail.foldLeft( (head, 1.0) ) { (r, c) =>
          ((r._1 + (c / r._2)) * r._2 / (r._2 + 1), r._2 + 1)
        }
        result._1
    }
  }


//  class Wire(var signal: Boolean)
//  def demux(in: Wire, c: List[Wire], out: List[Wire]) {
//    out match {
//      case x::xs =>
//        c.foldLeft( (in, 1) ) { (outSignal, c) =>
//          if (out.size == outSignal._2) {
//            (new Wire(outSignal._1.signal && c.signal), outSignal._2 + 1)
//          } else {
//            (new Wire(outSignal._1.signal && !c.signal), outSignal._2 + 1)
//          }
//        }
//        demux(in, c, xs)
//    }
//  }

}
