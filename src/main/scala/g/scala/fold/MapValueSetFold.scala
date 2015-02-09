package g.scala.fold

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object MapValueSetFold extends App {

  var m = Map[Int, String]()
  var s = m.foldLeft(Set[String]())(((r, c) => r + c._2))
  println(s)

  m = Map[Int, String](1 -> "v1", 2 -> "v2")
  s = m.foldLeft(Set[String]())((r, c) => r + c._2)
  println(s)

}
