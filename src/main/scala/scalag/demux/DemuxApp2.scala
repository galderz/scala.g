package scalag.demux

import org.scalatest.Assertions._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object DemuxApp2 extends App {

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
    def demux(in: Wire, c: List[Wire], accum: List[Wire]): List[Wire] = {
      c match {
        case Nil => in :: accum
        case x::xs =>
          val a1 = demux(new Wire(in.signal && x.signal), xs, accum)
          val a2 = demux(new Wire(in.signal && !x.signal), xs, a1)
          a2
      }
    }
    val tmp = demux(in, c, List()).reverse
    (tmp, out).zipped.foreach((x, y) => y.signal = x.signal)
  }

  // 0 control wires

  var out = List(new Wire(false))
  demux(new Wire(false), List(), out)
  assert(out == List(new Wire(false)))

  demux(new Wire(true), List(), out)
  assert(out == List(new Wire(true)))

  // 1 control wire

  out = List(new Wire(false), new Wire(false))

  demux(new Wire(false), List(new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(true)))

  demux(new Wire(false), List(new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(true)), out)
  assert(out === List(new Wire(true), new Wire(false)))

  // 2 control wires
  out = List(new Wire(false), new Wire(false), new Wire(false), new Wire(false))

  demux(new Wire(false), List(new Wire(false), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(false), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(true)))

  demux(new Wire(false), List(new Wire(false), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(false), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(true), new Wire(false)))

  demux(new Wire(false), List(new Wire(true), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(true), new Wire(false)), out)
  assert(out === List(new Wire(false), new Wire(true), new Wire(false), new Wire(false)))

  demux(new Wire(false), List(new Wire(true), new Wire(true)), out)
  assert(out === List(new Wire(false), new Wire(false), new Wire(false), new Wire(false)))

  demux(new Wire(true), List(new Wire(true), new Wire(true)), out)
  assert(out === List(new Wire(true), new Wire(false), new Wire(false), new Wire(false)))



  //        val a = new Wire(c(xs.size - 1).signal)
//        x.signal = in.signal && !a.signal
//        demux(in, c, out)

//    c match {
//      case Nil =>
//        val a = new Wire(true)
//        out(ctrlPos(c)).signal = in.signal && a.signal
//      case x::xs =>
//        val a = new Wire(false)
//        a.signal = !a.signal
//        out(ctrlPos(c)).signal = in.signal && a.signal
//        demux(in, xs, out)
//    }
//  }

//  private def ctrlPos(l: List[Wire]): Int = {
//    def ctrlPos(l: List[Wire], pos: Int): Int = {
//      l match {
//        case Nil => pos
//        case x::xs =>
//          ctrlPos(xs, pos << 1 | (if (x.signal) 1 else 0))
//      }
//    }
//
//    ctrlPos(l, 0)
//  }



}
