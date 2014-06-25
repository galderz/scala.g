package scalag.rx

import rx.lang.scala.{Observer, Observable}
import rx.lang.scala.subjects.ReplaySubject
import scala.concurrent.{Promise, Future}
import scala.util.Success
import scala.concurrent.ExecutionContext.Implicits.global

object CallbacksObservable extends App {

  val channel = ReplaySubject[Int]()

  def sub: Observable[Int] = {
    channel
  }

  def callback(i: Int) {
    channel.onNext(i)
  }

  for {
    x <- sub.subscribe()
  } yield {
    println(x)
  }

  // sub.subscribe(println(_))

  callback(9)
  callback(8)
  callback(7)

  implicit class ObservableOps[T](val o: Observable[T]) {
    def subscribe(): Future[T] = {
      val p = Promise[T]()
      o.subscribe(x => p.complete(Success(x)))
      p.future
    }

//    def subscribe(): Future[T] = {
//      val p = Promise[T]()
//      o.subscribe(x => p.complete(Success(x)))
//      p.future
//    }
  }

}
