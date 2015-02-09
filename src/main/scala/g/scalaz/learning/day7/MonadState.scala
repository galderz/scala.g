package scalazg.learning.day7

import g.scalaz.S

import scalaz._
import Scalaz._
import scalazg._

/**
 * http://eed3si9n.com/learning-scalaz/State.html
 */
object MonadState extends App {

  {
    // We can also implement pop and push in terms of get and put:
    type Stack = List[Int]
    val pop: State[Stack, Int] = for {
      s <- get[Stack]
      (x :: xs) = s
      _ <- put(xs)
    } yield x

    def push(x: Int): State[Stack, Unit] = for {
      xs <- get[Stack]
      r <- put(x :: xs)
    } yield r

    val newStack = for {
      s1 <- push(1)
      s2 <- push(2)
      s2 <- push(3)
      s2 <- push(4)
      s3 <- pop
    } yield s3
    S $ newStack(List())
  }

  {
    // LYAHFGG: We’ll say that a stateful computation is a function that takes some state
    // and returns a value along with some new state.
    // Scalaz: The important thing to note is that unlike the general monads we’ve seen,
    // State specifically wraps functions.
    type Stack = List[Int]
    // Using State[List[Int], Int] {...} we were able to abstract out the “extract state,
    // and return value with a state” portion of the code. The powerful part is the fact
    // that we can monadically chain each operations using for syntax without manually
    // passing around the Stack values as demonstrated in stackManip above.
    val pop = State[Stack, Int] {
      case x :: xs => (xs, x)
    }
    def push(a: Int) = State[Stack, Unit] {
      case xs => (a :: xs, ())
    }
    def stackManip: State[Stack, Int] = for {
      _ <- push(3)
      a <- pop
      b <- pop
    } yield b
    S $ stackManip(List(5, 8, 2, 1))
    // @infinispan: State would have been a good abstract for compiling Hot Rod/Memcached
    // operation parameters as headers and others aspects of the request are parsed?
    def stackyStack: State[Stack, Unit] = for {
      stackNow <- get
      r <- if (stackNow === List(1, 2, 3)) put(List(8, 3, 1)) else put(List(9, 2, 1))
    } yield r
    S $ stackyStack(List(1, 2, 3))
  }

  {
    // Haskell features a thing called the state monad, which makes dealing
    // with stateful problems a breeze while still keeping everything nice and pure.
    type Stack = List[Int]
    def pop(stack: Stack): (Int, Stack) = stack match {
      case x :: xs => (x, xs)
    }
    def push(a: Int, stack: Stack): (Unit, Stack) = ((), a :: stack)
    def stackManip(stack: Stack): (Int, Stack) = {
      val (_, newStack1) = push(3, stack)
      val (a, newStack2) = pop(newStack1)
      pop(newStack2)
    }
    S $ stackManip(List(5, 8, 2, 1))
  }

}
