package scalag.anyval

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object AnyVals extends App {

  val obj = new Object
  def getMyAnyVal = MyAnyVal(obj)
  def getMyAnyVal2 = new MyAnyVal(obj)


  val obj2 = new Object
  def getMyOtherAnyVal = new MyAnyOtherAnyVal(obj2)

  println(getMyAnyVal)
  println(getMyAnyVal)
  println(getMyAnyVal)
  println("=========")
  println(getMyAnyVal2)
  println(getMyAnyVal2)
  println(getMyAnyVal2)
  println("=========")
  println(new MyAnyVal(new Object))
  println(new MyAnyVal(new Object))
  println(new MyAnyVal(new Object))
  println("=========")
  println(getMyAnyVal2)
  println(getMyAnyVal2)
  println(getMyAnyVal2)
  println("=========")
  println(getMyOtherAnyVal.a())
  println(getMyOtherAnyVal.a())
  println(getMyOtherAnyVal.a())
  println(getMyOtherAnyVal.b())
  println(getMyOtherAnyVal.b())
  println(getMyOtherAnyVal.b())
  println("=========")
  val someOtherAnyVal = getMyOtherAnyVal
  println(someOtherAnyVal.b())
  println(someOtherAnyVal.a())
  println(someOtherAnyVal)

  println("=========")
  def obj3 = new Object;
  def getMyVaryingAnyVal = new MyAnyOtherAnyVal(obj3)
  println(getMyVaryingAnyVal.a())
  println("=========")

  println("=========")
  val x = getMyVaryingAnyVal
  println(x)
  println(x.a())
  println("=========")
}

class MyAnyVal(val obj: AnyRef) extends AnyVal

class MyAnyOtherAnyVal(val obj: AnyRef) extends AnyVal with A with B

trait A extends Any {
  def a(): A = this
}

trait B extends Any {
  def b(): B = this
}

object MyAnyVal {
  def apply(obj: AnyRef): MyAnyVal = {
    val anyVal = new MyAnyVal(obj)
    println("new MyAnyVal created: " + anyVal)
    anyVal
  }
}
