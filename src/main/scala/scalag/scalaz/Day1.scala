package scalag.scalaz

import scala.reflect.runtime.{universe=>u}
import scalaz._
import Scalaz._
import scala.Predef._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object Day1 extends App {

   private def p[T: u.TypeTag](obj: T) {
      val typeInfo = u.typeTag[T].tpe
      println(s"$typeInfo = $obj")
   }

   { // Equals
      p(1 === 1) // true

      // println(1 === "foo")
      // error: could not find implicit value for parameter F0: scalaz.Equal[Object]

      p(1 == "foo") // warning!

      p(1 === 2) // false
      p(1 =/= 2) // true
      p(1.some =/= 2.some) // true
      // p(1 assert_=== 2) // java.lang.RuntimeException: 1 ≠ 2
   }

   { // Order
      p(1 > 2.0) // false

      // println(1 gt 2.0)
      // could not find implicit value for parameter F0: scalaz.Order[Any]

      p(1.0 ?|? 2.0) // scalaz.Ordering = LT
      p(1.0 max 2.0) // Double = 2.0
   }

   { // Show
      p(3.show)
      p(3.shows)
   }

   { // Enum
      p('a' to 'e')
      p('a' |-> 'e')
      p(3 |=> 5)
      p('B'.succ)
   }

   { // Bounded
      p(implicitly[Enum[Char]].min)
      p(implicitly[Enum[Char]].max)
      p(implicitly[Enum[Int]].min)
   }

   {
      sealed trait TrafficLight
      case object Red extends TrafficLight
      case object Yellow extends TrafficLight
      case object Green extends TrafficLight

      implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)

      // p(Red === Yellow)
      // error: could not find implicit value for parameter F0: scalaz.Equal[Product with Serializable with TrafficLight]
   }

   {
      case class TrafficLight(name: String)
      val red = TrafficLight("red")
      val yellow = TrafficLight("yellow")
      val green = TrafficLight("green")
      implicit val TrafficLightEqual: Equal[TrafficLight] = Equal.equal(_ == _)
      p(red === yellow) // IDE wrong! Compilation works!
   }

   {
      trait CanTruthy[A] { self =>
         /** @return true, if `a` is truthy. */
         def truthys(a: A): Boolean
      }
      object CanTruthy {
         def apply[A](implicit ev: CanTruthy[A]): CanTruthy[A] = ev
         def truthys[A](f: A => Boolean): CanTruthy[A] = new CanTruthy[A] {
            def truthys(a: A): Boolean = f(a)
         }
      }
      trait CanTruthyOps[A] {
         def self: A
         implicit def F: CanTruthy[A]
         final def truthy: Boolean = F.truthys(self)
      }
      object ToCanIsTruthyOps {
         implicit def toCanIsTruthyOps[A](v: A)(implicit ev: CanTruthy[A]) =
            new CanTruthyOps[A] {
               def self = v
               implicit def F: CanTruthy[A] = ev
            }
      }

      import ToCanIsTruthyOps._ // IMPORTANT!

      implicit val IntCanTruthy: CanTruthy[Int] = CanTruthy.truthys({
         case 0 => false
         case _ => true
      })

      p(10.truthy)

      implicit def listCanTruthy[A]: CanTruthy[List[A]] = CanTruthy.truthys({
         case Nil => false
         case _   => true
      })

      List("foo").truthy

      implicit val nilCanTruthy: CanTruthy[scala.collection.immutable.Nil.type] =
         CanTruthy.truthys(_ => false)

      def truthyIf[A: CanTruthy, B, C](cond: A)(ifyes: => B)(ifno: => C) =
         if (cond.truthy) ifyes
         else ifno

      p(truthyIf (Nil) {"YEAH!"} {"NO!"})
   }

}
