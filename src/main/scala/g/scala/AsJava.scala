package g.scala

class AsJava[+A](op: => A) {
  /** Converts a Scala collection to the corresponding Java collection */
  def asJava: A = op
}
