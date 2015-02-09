package g.scala.patternmatch

import scala.annotation.switch

/**
 * @author Galder Zamarreño
 */
object MatchLikeSwitchStatement extends App {

  val i = 1
  val x = (i: @switch) match {
    case 1  => "One"
    case 2  => "Two"
    case _  => "Other"
  }

  // Does not compile:
  //
  //  val y = (i: @switch) match {
  //    case 1  => "One"
  //    case "Two"  => "Two"
  //    case _  => "Other"
  //  }

  println(x)

}
