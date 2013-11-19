package scalag.demux

import scalag.demux.DemuxApp2.Wire

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object Boo extends App {

  val l1 = List(new Wire(true), new Wire(true))
  val l2 = List(new Wire(false), new Wire(false))

  (l1, l2).zipped.foreach((x, y) => y.signal = x.signal)

  println(l2)

  println(Math.pow(2, 0) - 1)
}
