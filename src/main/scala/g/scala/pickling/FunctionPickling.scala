package g.scala.pickling

import scala.pickling._
import scala.pickling.Defaults._, scala.pickling.json._

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

