package scalag.typeclass

import org.joda.time.Duration
import org.joda.time.Duration._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object TypeClassExample extends App {

   trait NumberLike[T] {
      def plus(x: T, y: T): T
      def divide(x: T, y: Int): T
      def minus(x: T, y: T): T
   }

   implicit object NumberLikeDouble extends NumberLike[Double] {
      def plus(x: Double, y: Double): Double = x + y
      def divide(x: Double, y: Int): Double = x / y
      def minus(x: Double, y: Double): Double = x - y
   }

   implicit object NumberLikeInt extends NumberLike[Int] {
      def plus(x: Int, y: Int): Int = x + y
      def divide(x: Int, y: Int): Int = x / y
      def minus(x: Int, y: Int): Int = x - y
   }

   implicit object NumberLikeDuration extends NumberLike[Duration] {
      def plus(x: Duration, y: Duration): Duration = x.plus(y)
      def divide(x: Duration, y: Int): Duration = Duration.millis(x.getMillis / y)
      def minus(x: Duration, y: Duration): Duration = x.minus(y)
   }

   def mean[T](xs: Vector[T])(implicit ev: NumberLike[T]): T =
      ev.divide(xs.reduce(ev.plus(_, _)), xs.size)

   def median[T : NumberLike](xs: Vector[T]): T = xs(xs.size / 2)

   def quartiles[T: NumberLike](xs: Vector[T]): (T, T, T) =
      (xs(xs.size / 4), median(xs), xs(xs.size / 4 * 3))

   def iqr[T: NumberLike](xs: Vector[T]): T = quartiles(xs) match {
      case (lowerQuartile, _, upperQuartile) =>
         implicitly[NumberLike[T]].minus(upperQuartile, lowerQuartile)
   }

   val numbers = Vector[Double](13, 23.0, 42, 45, 61, 73, 96, 100, 199, 420, 900, 3839)
   println(mean(numbers))

   val durations = Vector(standardSeconds(20), standardSeconds(57), standardMinutes(2),
      standardMinutes(17), standardMinutes(30), standardMinutes(58), standardHours(2),
      standardHours(5), standardHours(8), standardHours(17), standardDays(1),
      standardDays(4))
   println(mean(durations).getStandardHours)

}
