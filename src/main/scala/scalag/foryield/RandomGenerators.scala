package scalag.foryield

import java.util.Random

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object RandomGenerators extends App {

  trait Generator[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate: S = f(self.generate)
    }

    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
      def generate: S = f(self.generate).generate
    }
  }

  val integers = new Generator[Int] {
    val rand = new Random
    def generate: Int = rand.nextInt()
  }

  // This is too much boilter plate code, see better version below
  //  val booleans = new Generator[Boolean] {
  //    def generate: Boolean = integers.generate > 0
  //  }

  val booleans = for (x <- integers) yield x > 0

  // This is too much boilter plate code, see better version below
  //  val pairs = new Generator[(Int, Int)] {
  //    def generate: (Int, Int) = (integers.generate, integers.generate)
  //  }

  def pairs[T, U](t: Generator[T], u: Generator[U]) = for {
    x <- t
    y <- u
  } yield (x, y)

  def single[T](x: T): Generator[T] = new Generator[T] {
    def generate: T = x
  }

  def choose(lo: Int, hi: Int): Generator[Int] =
    for (x <- integers) yield lo + x % (hi - lo)

  def oneOf[T](xs: T*): Generator[T] =
    for (idx <- choose(0, xs.length)) yield xs(idx)

  def lists: Generator[List[Int]] = for {
    isEmpty <- booleans
    list <- if (isEmpty) emptyLists else nonEmptyLists
  } yield list

  def emptyLists = single(Nil)

  def nonEmptyLists = for {
    head <- integers
    tail <- lists
  } yield head :: tail

  // Tree Generator

  trait Tree
  case class Inner(left: Tree, right: Tree) extends Tree
  case class Leaf(x: Int) extends Tree

  def leafs: Generator[Leaf] = for {
    x <- integers
  } yield Leaf(x)

  def inners: Generator[Tree] = for {
    l <- trees
    r <- trees
  } yield Inner(l, r)

  def trees: Generator[Tree] = for {
    isLeaf <- booleans
    tree <- if (isLeaf) leafs else inners
  } yield tree

  println(trees.generate)
}
