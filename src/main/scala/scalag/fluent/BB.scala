package scalag.fluent

import javag.fluent.{A, B}

import scalag.AsJava

trait BB[T] extends AA {
  def bbb(): BB[T]
}
