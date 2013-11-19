package scalag.demux

import org.scalatest.Assertions._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object DemuxApp extends App {

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
    out match {
      case Nil =>
      case x::xs =>
        val accum = new Wire(false)
        def ctrl(c: List[Wire], accum: Wire): Wire = {
          c match {
            case Nil =>
              println((out.size / Math.pow(2, 1)) % 2)
              // println(xs.size)
//              println(xs.size)
              new Wire(false)
//              val truth = Math.pow(2, c.size) % 2
//              truth match {
//                case 0 => new Wire(!accum.signal)
//                case 1 => new Wire(accum.signal)
//              }
            case e::es =>
              // println(xs.size)
              println((xs.size / Math.pow(2, es.size)) % 2)
              ctrl(es, new Wire(false))
//              val truth = Math.pow(2, es.size) % 2
//              truth match {
//                case 0 => ctrl(es, new Wire(e.signal && !accum.signal))
//                case 1 => ctrl(es, new Wire(e.signal && accum.signal))
//              }
          }
        }

        c match {
          case Nil =>
            x.signal = in.signal
          case e::es =>
            val s = ctrl(c, accum).signal
            x.signal = in.signal && s
        }

        demux(in, c, xs)
    }
  }

    var out = List(new Wire(false))
    demux(new Wire(false), List(), out)
    assert(out == List(new Wire(false)))

    demux(new Wire(true), List(), out)
    assert(out == List(new Wire(true)))

    // --
    demux(new Wire(true), List(new Wire(true)), out)
    assert(out === List(new Wire(true), new Wire(false)))

//    out = List(new Wire(false), new Wire(false))
//    demux(new Wire(false), List(new Wire(false)), out)
//    assert(out === List(new Wire(false), new Wire(false)))
//
//    out = List(new Wire(false), new Wire(false))
//    demux(new Wire(true), List(new Wire(false)), out)
//    assert(out === List(new Wire(false), new Wire(true)))
//
//    demux(new Wire(false), List(new Wire(true)), out)
//    assert(out === List(new Wire(false), new Wire(false)))







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
