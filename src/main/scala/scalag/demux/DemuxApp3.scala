package scalag.demux

import org.scalatest.Assertions._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object DemuxApp3 extends App {

  class Wire(sig: Boolean) {
    var signal = sig

    override def equals(obj: scala.Any): Boolean = {
      obj match {
        case w: Wire => w.signal == this.signal
        case _ => false
      }
    }

    override def toString: String = signal.toString
  }

  def demux(in: Wire, c: List[Wire], out: List[Wire]) {
    c match {
      case Nil => out.head.signal = in.signal
      case x::Nil =>
        out.head.signal = in.signal && !x.signal
        out.tail.head.signal = in.signal && x.signal
      case x::xs =>
        val a1 = new Wire(in.signal && !x.signal)
        val a2 = new Wire(in.signal && x.signal)
        val halved = out.splitAt(out.size / 2)
        demux(a1, xs, halved._1)
        demux(a2, xs, halved._2)
    }



      //      def demuxWithControl(in: Wire, c: List[Wire], out: List[Wire]): Wire = {
//        c match {
//          case Nil => in
//          case x::xs =>
//            val newIn1 = in.signal && x.signal
//            val newIn2 = in.signal && !x.signal
//            if (xs.isEmpty) {
//              out(Math.pow(2, c.size).toInt - 1).signal = newIn1
//              out(Math.pow(2, c.size).toInt - 2).signal = newIn2
//              in
//            } else {
//              val out1 = demuxWithControl(new Wire(newIn1), xs, out)
//              val out2 = demuxWithControl(new Wire(newIn2), xs, out)
//              out(Math.pow(2, c.size).toInt - 1).signal = out1.signal
//              out(Math.pow(2, c.size).toInt - 2).signal = out2.signal
//              in
//            }
//        }
//      }
//
//      demuxWithControl(in, c, out)
//    }
  }

  // 0 control wires

  var out = List(new Wire(false))
  var in = new Wire(false)
  var c = List[Wire]()
  demux(in, c, out)
  assert(out == List(new Wire(false)))

  in = new Wire(true)
  demux(in, c, out)
  assert(out == List(new Wire(true)))

  // 1 control wire

  out = List(new Wire(false), new Wire(false))

  in = new Wire(false)
  c = List(new Wire(false))
  demux(in, c, out)
  assert(out === List(new Wire(false), new Wire(false)))

  in = new Wire(true)
  c = List(new Wire(false))
  demux(in, c, out)
  assert(out === List(new Wire(true), new Wire(false)))

  in = new Wire(false)
  c = List(new Wire(true))
  demux(in, c, out)
  assert(out === List(new Wire(false), new Wire(false)))

  in = new Wire(true)
  c = List(new Wire(true))
  demux(in, c, out)
  assert(out === List(new Wire(false), new Wire(true)))

  // 2 control wires
  out = List(new Wire(false), new Wire(false), new Wire(false), new Wire(false))

  in = new Wire(false)
  c = List(new Wire(false), new Wire(false))
  demux(in, c, out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  in = new Wire(true)
  c = List(new Wire(false), new Wire(false))
  demux(in, c, out)
  assert(out === List(new Wire(true), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(false), List(new Wire(false), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(false), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(true), new Wire(false), new Wire(false)))

  demux(new Wire(false), List(new Wire(true), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(true), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(true), new Wire(false)))

  demux(new Wire(false), List(new Wire(true), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(true), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(true)))

}
