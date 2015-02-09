import scala.reflect.runtime.universe._

class Foo
class Bar extends Foo

def meth[A : TypeTag](xs: List[A]) = typeOf[A] match {
  case t if t =:= typeOf[String] => "list of strings"
  case t if t <:< typeOf[Foo] => "list of foos"
}

meth(List("string"))
meth(List(new Bar))
