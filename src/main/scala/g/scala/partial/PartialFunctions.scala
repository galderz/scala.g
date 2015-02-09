package g.scala.partial

object PartialFunctions extends App {

  val g: PartialFunction[List[Int], String] = {
    case Nil => "one"
    case x :: rest =>
      rest match {
        case Nil => "two"
      }
  }

  // Returns true because isDefinedAt only applies to the outer pattern match
  println(g.isDefinedAt(List(1, 2, 3)))

}
