// Typeclass defines things that can be handled
trait Handleable[T] {
  def handle(fn: T => Unit)
}

class A {
  def x(fn: A => Unit): Unit = fn(this)
  override def toString = "A"
}

class B {
  def y(fn: B => Unit): Unit = fn(this)
  override def toString = "B"
}

// Two typeclass instances, one for A and one for B
implicit object AHandleable extends Handleable[A] {
  def handle(fn: A => Unit) = new A().x(fn)
}

implicit object BHandleable extends Handleable[B] {
  def handle(fn: B => Unit) = new B().y(fn)
}

//def handle[T](f: T => Unit)(implicit h: Handleable[T]) =
//  h.handle(f)

def handle[T: Handleable](f: T => Unit) =
  implicitly[Handleable[T]].handle(f)

handle[A] { a: A =>
  println("It's " + a)
}

handle[B] { b: B =>
  println("It's " + b)
}
