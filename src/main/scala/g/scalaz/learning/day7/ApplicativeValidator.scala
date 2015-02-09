package scalazg.learning.day7

import g.scalaz.S

import scalaz._
import Scalaz._
import scalazg._

/**
 * http://eed3si9n.com/learning-scalaz/Validation.html
 */
object ApplicativeValidator extends App {

  {
    // This is a wrapper trait for plain List that’s guaranteed to be non-empty.
    // Since there’s at least one item in the list, head always works.
    // IdOps adds wrapNel to all data types to create a Nel.
    S $ (("event 1 ok".successNel[String]
      |@| "event 2 failed!".failureNel[String]
      |@| "event 3 failed!".failureNel[String]) {_ + _ + _})
    S $ "event 1 ok".successNel[String]
    S $ "event 1 failed!".failureNel[String]
    S $ 1.wrapNel

    // What’s different about Validation is that it is not a monad, but it’s an applicative functor.
    // Instead of chaining the result from first event to the next, Validation validates all events.
    // It’s a bit difficult to see, but the final result is Failure(event 2 failed!event 3 failed!).
    // Unlike \/ monad which cut the calculation short, Validation keeps going and reports back all failures.
    // This probably would be useful for validating user’s input on an online bacon shop.
    // The problem, however, is that the error messages are mushed together into one string.
    // Shouldn’t it be something like a list? This is where NonEmptyList (or Nel for short) comes in.
    S $ (("event 1 ok".success[String]
      |@| "event 2 failed!".failure[String]
      |@| "event 3 failed!".failure[String]) {_ + _ + _})

    // Another data structure that’s compared to Either in Scalaz is Validation
    // ValidationV introduces success[X], successNel[X], failure[X], and failureNel[X] methods
    // to all data types (don’t worry about the Nel thing for now)
    S $ "event 1 ok".success[String]
    S $ "event 1 failed!".failure[String]
  }

}
