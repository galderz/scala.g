package g.scala.rx

import rx.lang.scala.Observable
import scala.concurrent.duration._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ObservablesExamples extends App {

  val xs: Observable[Int] = Observable(3, 2, 1)
  xs.subscribe(x => println("boo" + x))

  val yss: Observable[Observable[Int]] =
    xs.map(x => Observable.interval(x seconds).map(_ =>x).take(2))
  val zs: Observable[Int] = yss.flatten

  val subscription = zs.subscribe(println(_))

  while(true) {
    // todo
  }

}
