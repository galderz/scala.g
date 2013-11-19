package scalag.demux

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object BigEndian extends App {

//  def bitvec2int(l: List[Boolean]): Int = {
//    var x = 0
//    l.foreach(b => x = x << 1 | (if (b) 1 else 0))
//    x
//  }

  def bitvec2int(l: List[Boolean]): Int = {
    def rec(l: List[Boolean], carry: Int): Int = {
      l match {
        case Nil => carry
        case x::xs =>
          rec(xs, carry << 1 | (if (x) 1 else 0))
      }
    }

    rec(l, 0)
  }


  println(bitvec2int(List()))
  println(bitvec2int(List(false)))
  println(bitvec2int(List(true)))
  println(bitvec2int(List(false, false)))
  println(bitvec2int(List(false, true)))
  println(bitvec2int(List(true, false)))
  println(bitvec2int(List(true, true)))

}
