class A {
  def x(fn: A => Unit): Unit = fn(this)
}

class B {
  def y(fn: B => Unit): Unit = fn(this)
}

def handle[T](fn: Function1[T, Unit]): Unit = {
  fn match {
    case fnA: Function1[A, Unit] =>
      new A().x(fnA)
    case fnB: Function1[B, Unit] =>
      new B().y(fnB)
  }
}

handle[A] { a: A =>
  println("It's " + a)
}

handle[B] { b: B =>
  println("It's " + b)
}
