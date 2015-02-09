package g.scala.pickling

import scala.pickling._
import binary._

/**
 * @author Galder Zamarreño
 */
object FunctionPickling extends App {

  val string = "galder"
  val pickle = string.pickle
  println(pickle.unpickle[String])

  val functionPickle = (() => "galder").pickle
  val function = functionPickle.unpickle[() => String]
  println(function.apply())

}

