package g.scala.pickling

import scala.pickling._
import binary._

/**
 * @author Galder Zamarreño
 */
object MultiTypePickling extends App {

  sealed abstract class Base
  final class A extends Base { override def toString = "A" }
  final class B extends Base { override def toString = "B" }

  val a = new A
  val pa = a.pickle
  val b = new B
  val pb = b.pickle
  // ----
  pa.value.unpickle[Base] match {
    case aa: A =>
      println("It's " + aa)
    case _ =>
      assert(assertion = false)
  }

  pb.value.unpickle[Base] match {
    case bb: B =>
      println("It's " + bb)
    case _ =>
      assert(assertion = false)
  }

//  val a: Base = new A
//  val pa = a.pickle
//  assert(pa.value.unpickle[Base].isInstanceOf[A])
//
//  val b: Base = new B
//  val pb = b.pickle
//  assert(pb.value.unpickle[Base].isInstanceOf[B])


//  val a: Base = new A
//  val pc = a.pickle
//  assert(pc.unpickle[Base].isInstanceOf[A])
//
//  val b: Base = new B
//  val pd = b.pickle
//  assert(pd.unpickle[Base].isInstanceOf[B])
  
}
