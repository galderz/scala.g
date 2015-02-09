package g.scala.fold

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

  def last[A](l: List[A]): A = l.foldLeft[A](l.head) {(r, c) => c}
  println(last(List(1, 2, 3)))

  def penultimate[A](l: List[A]): A =
    l.foldLeft( (l.head, l.tail.head) ) { (r, c) =>
      (r._2, c)
    }._1
  println(penultimate(List(3, 2, 1)))

  def contains[A](l: List[A], e: A): Boolean =
    l.foldLeft(false) { (r, c) => r || c == e }
  println(contains(List(1, 2, 3, 4), 5))
  println(contains(List(1, 2, 3, 4), 2))

  def get[A](l: List[A], i: Int): Option[A] =
    l.foldLeft((l.head, 0)) {
      (r, c) => if (r._2 == i) r else (c, r._2 + 1)
    } match {
      case (result, index) if index == i => Some(result)
      case _ => None
    }
  println(get(List(1, 2, 3, 4), 5))
  println(get(List(1, 2, 3, 4), 3))

  def mimicToString[A](l: List[A]): String = l match {
    case Nil => "List()"
    case x::xs => xs.foldLeft(s"List($x") { (r, c) => s"$r, $c"} + ")"
  }
  println(mimicToString(List(1, 2, 3)))

  def reverse[A](l: List[A]): List[A] = l.foldLeft(List[A]()){ (r, c) => c :: r}
  println(reverse(List(1, 2, 3)))

  def unique[A](l: List[A]): List[A] =
    l.foldLeft(List[A]()) { (r, c) => if (r.contains(c)) r else c :: r}.reverse
  println(unique(List(1, 1, 2, 3, 2)))

  def toSet[A](l: List[A]): Set[A] = l.foldLeft(Set[A]())( (r,c) => r + c)
  println(toSet(List(1, 1, 2, 3, 2)))

  def double[A](l: List[A]): List[A] =
    l.foldLeft(List[A]())((r, c) => c :: c :: r).reverse

  def insertionSort[A <% Ordered[A]](list: List[A]): List[A] =
    list.foldLeft(List[A]()) { (r,c) =>
      val (front, back) = r.span(_ < c)
      front ::: c :: back
    }

  def pivot[A <% Ordered[A]](list: List[A]): (List[A],A,List[A]) =
    list.tail.foldLeft[(List[A],A,List[A])]( (Nil, list.head, Nil) ) {
      (result, item) =>
        val (r1, pivot, r2) = result
        if (item < pivot) (item :: r1, pivot, r2) else (r1, pivot, item :: r2)
    }

  def quicksort[A <% Ordered[A]](list: List[A]): List[A] = list match {
    case head :: _ :: _ =>
      println(list)
      list.foldLeft[(List[A],List[A],List[A])]( (Nil, Nil, Nil) ) {
        (result, item) =>
          val (r1, r2, r3) = result
          if      (item < head) (item :: r1, r2, r3)
          else if (item > head) (r1, r2, item :: r3)
          else                  (r1, item :: r2, r3)
      } match {
        case (list1, list2, list3) =>
          quicksort(list1) ::: list2  ::: quicksort(list3)
      }
    case _ => list
  }

  def encode[A](list: List[A]): List[(A,Int)] =
    list.foldLeft(List[(A,Int)]()){ (r,c) =>
      r match {
        case (value, count) :: tail =>
          if (value == c) (c, count+1) :: tail
          else            (c, 1) :: r
        case Nil =>
          (c, 1) :: r
      }
    }.reverse

  def decode[A](list: List[(A,Int)]): List[A] =
    list.foldLeft(List[A]()){ (r,c) =>
      var result = r
      for (_ <- 1 to c._2) result = c._1 :: result
      result
    }.reverse

  def group[A](list: List[A], size: Int): List[List[A]] =
    list.foldLeft( (List[List[A]](),0) ) { (r,c) => r match {
      case (head :: tail, num) =>
        if (num < size)  ( (c :: head) :: tail , num + 1 )
        else             ( List(c) :: head :: tail , 1 )
      case (Nil, num) => (List(List(c)), 1)
    }
    }._1.foldLeft(List[List[A]]())( (r,c) => c.reverse :: r)

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
