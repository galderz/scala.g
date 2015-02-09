package scalazg.book

import scala.annotation.tailrec

object GettingStarted extends App {

  // Exercise 2.5
  def compose[A,B,C](f: B => C, g: A => B): A => C = {
    (a: A) => f(g(a))
  }

  // Exercise 2.4
  def uncurry[A, B, C](f: A => B => C): (A, B) => C = {
    (a: A, b: B) => f(a)(b)
  }

  // Exercise 2.3
  def curry[A, B, C](f: (A, B) => C): A => (B => C) = {
    (a: A) => (b: B) => f(a, b)
  }
  def partial1[A, B, C](a: A, f: (A, B) => C): B => C = {
    (b: B) => f(a, b)
  }

  // Exercise 2.2
  def isSorted[A](as: Array[A], ordered: (A, A) => Boolean): Boolean = {
    @tailrec
    def loop(e1: A, e2: A, n: Int): Boolean = {
      if (n >= as.length - 1) ordered(e1, e2)
      else if (ordered(e1, e2)) loop(as(n), as(n + 1), n + 1)
      else false
    }

    loop(as.head, as.tail.head, 1)
  }

  println(isSorted[Int](Array(1, 2, 3), (x, y) => x < y))
  println(isSorted[Int](Array(2, 1, 3), (x, y) => x < y))
  println(isSorted[Int](Array(1, 3, 2), (x, y) => x < y))

  println(isSorted[Int](Array(3, 2, 1), (x, y) => x > y))
  println(isSorted[Int](Array(2, 1, 3), (x, y) => x > y))
  println(isSorted[Int](Array(1, 3, 2), (x, y) => x > y))

}
