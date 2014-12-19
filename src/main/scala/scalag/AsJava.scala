package scalag

class AsJava[+A](op: => A) {
  /** Converts a Scala collection to the corresponding Java collection */
  def asJava: A = op
}
