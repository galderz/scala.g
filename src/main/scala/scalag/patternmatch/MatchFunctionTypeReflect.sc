import scala.reflect.runtime.universe._

class A {
  def x(fn: A => Unit): Unit = fn(this)
  override def toString = "A"
}
class B {
  def y(fn: B => Unit): Unit = fn(this)
  override def toString = "B"
}

def handle[T : TypeTag](fn: Function1[T, Unit]): Unit = typeOf[T] match {
  case t if t =:= typeOf[A] => new A().x(fn.asInstanceOf[Function1[A, Unit]])
  case t if t =:= typeOf[B] => new B().y(fn.asInstanceOf[Function1[B, Unit]])
}

handle[A] { a: A =>
  println("It's " + a)
}
handle[B] { b: B =>
  println("It's " + b)
}
