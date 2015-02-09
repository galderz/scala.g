package g.scala.functional

/**
 * Writer Monad example
 */
object WriterMonad extends App {

  val add: Int => Int => Int =
    x => y => x + y

  val mult: Int => Int => Int =
    x => y => x * y

  val div: Int => Int => Option[Double] =
    x => y => if (y == 0) None else Some(x.toDouble / y)

  val parse: String => Option[Int] =
    x => try { Some(x.toInt) } catch { case t: Throwable => None }

  // Option 4: Logging with Writer Monad
  println(CompoundOpLoggingWithWriterMonad())

  // Option 3: Logging with specialised LogOption class
  // println(CompoundOpLoggingWithOption())

  // Option 2: Logging with side effects
  // println(CompoundOpLoggingWithSideEffects())

  // Option 1: No logging
  // println(CompoundOpNoLogging())

}

object CompoundOpLoggingWithWriterMonad {
  import WriterMonad._

  trait Functor[F[_],A] {
    def map[B](f: A => B): F[B]
  }

  trait Monad[F[_],A] extends Functor[F,A] {
    def flatMap[B](f: A => F[B]): F[B]
  }

  trait LiftM[F[_]] {
    def apply[A](x: F[A]): Monad[F,A]
  }

  trait Semigroup[A] {
    def *(a: A): A
  }

  implicit def semigroup[A](x: Seq[A]): Semigroup[Seq[A]] =
    new Semigroup[Seq[A]] {
      def *(as: Seq[A]): Seq[A] = x ++ as
    }

  case class ID[A](a: A)

  case class WriterT[F[_],W,A](run: F[(A,W)])
  type Writer[W,A] = WriterT[ID,W,A]

  type WriterTM[F[_],W,A] = Monad[({type λ[α] = WriterT[F,W,α]})#λ,A]

  implicit def writerT[F[_],W,A](x: WriterT[F,W,A])
    (implicit liftM: LiftM[F], liftS: W => Semigroup[W]): WriterTM[F,W,A] =
    new WriterTM[F,W,A] { // ignore IDE error
      def run: F[(A,W)] = x.run
      def map[B](f: A => B): WriterT[F,W,B] =
        new WriterT[F,W,B](liftM(x.run) map { x => (f(x._1), x._2) })
      def flatMap[B](f: A => WriterT[F,W,B]): WriterT[F,W,B] =
        new WriterT[F,W,B](
          liftM(x.run) flatMap { case (a,w1) =>
            liftM(f(a).run) map { case (b,w2) => (b, w1 * w2) } })
    }

  type LogT[F[_],A] = WriterT[F,Seq[String],A]

  def logT[F[_],A](x: F[A])(implicit lift: F[A] => Monad[F,A]): LogT[F,A] =
    new LogT(x.map(a => (a, Nil))) // ignore IDE error

  def log(x: String)(implicit lift: Seq[String] => Semigroup[Seq[String]]): LogT[Option,Unit] =
    new LogT(Some(((), Seq(x))))

  implicit val liftOption: LiftM[Option] =
    new LiftM[Option] {
      def apply[A](x: Option[A]): Monad[Option,A] = x
    }

  implicit def optionMonad[A](x: Option[A]): Monad[Option,A] =
    new Monad[Option,A] {
      def map[B](f: A => B): Option[B] = x.map(f)
      def flatMap[B](f: A => Option[B]): Option[B] = x.flatMap(f)
    }

  def apply(): Option[(Double, Seq[String])] = {
    val x = for {
      x1 <- logT(parse("42"))  // lift Option[Int] to LogT[Int]
      _  <- log("x1: " + x1)
      x2  = mult(x1)(2)
      _  <- log("x2: " + x2)
      x3  = add(x2)(42)
      _  <- log("x3: " + x3)
      x4 <- logT(div(x3)(3))   // lift Option[Double] to LogT[Double]
      _  <- log("x4: " + x4)
    } yield x4
    x.run  // returns Some((42.0,List(x1: 42, x2: 84, x3: 126, x4: 42.0)))
  }
}

object CompoundOpLoggingWithOption {
  import WriterMonad._

  class LogOption[A](val run: Option[(A, Seq[String])]) {
    def map[B](f: A => B): LogOption[B] =
      new LogOption(run map { x => (f(x._1), x._2) })
    def flatMap[B](f: A => LogOption[B]): LogOption[B] =
      new LogOption(run flatMap { case (a, l) =>
        f(a).run map { case (b, l2) => (b, l ++ l2) } })
  }

  implicit def logOption[A](x: Option[A]): LogOption[A] =
    new LogOption(x.map(a => (a, Nil)))

  def log(x: String): LogOption[Unit] =
    new LogOption(Some(((), Seq(x))))

  def apply(): Option[(Double, Seq[String])] = {
    val x = for {
      x1 <- logOption(parse("42"))  // lift Option[Int] to LogOption[Int]
      _  <- log("x1: " + x1)
      x2  = mult(x1)(2)
      _  <- log("x2: " + x2)
      x3  = add(x2)(42)
      _  <- log("x3: " + x3)
      x4 <- logOption(div(x3)(3))   // lift Option[Double] to LogOption[Double]
      _  <- log("x4: " + x4)
    } yield x4
    x.run  // returns Some((42.0,List(x1: 42, x2: 84, x3: 126, x4: 42.0)))
  }
}

object CompoundOpLoggingWithSideEffects {
  import WriterMonad._
  def apply(): Option[Double] = {
    for {
      x1 <- parse("42")
      _  = println("x1: " + x1)  // prints "x1: 42" to stdout
      x2  = mult(x1)(2)
      _  = println("x2: " + x2)  // prints "x2: 84" to stdout
      x3  = add(x2)(42)
      _  = println("x3: " + x3)  // prints "x3: 126" to stdout
      x4 <- div(x3)(3)
      _  = println("x4: " + x4)  // prints "x4: 42.0" to stdout
    } yield x4                    // returns Some(42.0)
  }
}

object CompoundOpNoLogging {
  import WriterMonad._
  def apply(): Option[Double] = {
    for {
      x1 <- parse("42")
      x2  = mult(x1)(2)
      x3  = add(x2)(42)
      x4 <- div(x3)(3)
    } yield x4  // returns Some(42.0)
  }
}
