import scala.pickling._
import scala.pickling.binary._

val string = "galder"
val pickle = string.pickle

pickle.unpickle[String]

object Foo {
  def mkString() = { () => "galder"}
}
val functionPickle = Foo.mkString().pickle
functionPickle.unpickle[Function0[String]]
