package g.scala.foryield

import scala.util.{Failure, Success, Try}
import java.util.Random

object HandlerPrototype extends App {

  trait Generator[+T] {
    self =>

    def generate: T

    def map[S](f: T => S): Generator[S] = new Generator[S] {
      def generate: S = f(self.generate)
    }

//    def flatMap[S](f: T => Generator[S]): Generator[S] = new Generator[S] {
//      def generate: S = f(self.generate).generate
//    }
  }

  val integers = new Generator[Int] {
    val rand = new Random
    def generate: Int = rand.nextInt()
  }

  // This is too much boilter plate code, see better version below
  //  val booleans = new Generator[Boolean] {
  //    def generate: Boolean = integers.generate > 0
  //  }

  integers.map(println(_))

  private val booleans = for {
    x <- integers
  } yield {
    println(x)
    x > 0
  }


  booleans.generate
  booleans.generate
  booleans.generate

//  trait Handler[+T] { self =>
//    def handle: T
//    def map[S](f: T => S): Handler[S] = new Handler[S] {
//      def handle: S = f(self.handle)
//    }
//    def flatMap[S](f: T => Handler[S]): Handler[S] = new Handler[S] {
//      def handle: S = f(self.handle).handle
//    }
//  }
//
//  def handle(): Handler[Try[Int]] = {
//
//  }
//
//  class TryHandler(ext: ExternalHandler) extends Handler[Try[Int]] {
//    override def handle: Try[Int] =
//      if (ext.success) Success(ext.i)
//      else Failure(new Exception("Whatever"))
//  }
//
//  class ExternalHandler() {
//    def handle(i: Int, success: Boolean): Unit = {
//
//    }
//  }

}
