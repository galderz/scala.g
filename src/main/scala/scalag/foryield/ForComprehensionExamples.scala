package scalag.foryield

import java.util

import scala.concurrent.{ExecutionContext, Future}
import ExecutionContext.Implicits.global

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ForComprehensionExamples {

  def main(args: Array[String]) {
    val x = "galder"
    (for {
      dm <- Some(x)
      location <- Some(dm)
    } yield {
      location.toString
    }).getOrElse {
      null
    }

    {for {
      dm <- Future(Some(x))
      location <- Future(Some(dm))
    } yield {
      location.get.toString
    }} recover {
      case e => 0
    }

    println(util.Arrays.toString(adults(List(1, 30, 19))))
    println(util.Arrays.toString(adults2(List(60, 10, 34))))
  }

  def adults(people: List[Int]): Array[Int] = {
    val adults =
      for {
        p <- people
        if p >= 18
      } yield p

    adults.toArray // <- Results in 2nd iteration!! Not good :(
  }

  def adults2(people: List[Int]): Array[Int] = {
    (for {
      p <- people
      if p >= 18
    } yield p)(collection.breakOut)
  }

  def boo(): Option[Boolean] = {
    for {
      x <- Some("he")
      y <- Option(x)
      z <- Option(x)
      if !z.isEmpty
    } yield {
      true
    }
  }

}
