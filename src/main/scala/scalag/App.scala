package scalag

import java.util.concurrent.ConcurrentHashMap

/**
 * @author ${user.name}
 */
object App {

  def foo1(x : Array[String]) = x.foldLeft("")((a,b) => a + b)
  def foo2(x : Array[String]) = x.foldRight("")((a,b) => a + b)

  def main(args : Array[String]) {
    println( "Hello World!" )
//    methodA(println(_))
//    methodB(println("Boo"))
//    methodA
//    println("concat arguments (foldLeft)  = " + foo1(args))
//    println("concat arguments (foldRight) = " + foo2(args))
//
//    val chm = new ConcurrentHashMap[Integer, String]()
//    chm.put(1, "v1")
//    println(chm.get(1))
//    println(chm.replace(1, "v1", "v1"))
  }

  def methodA(x: String => Unit): Unit = {
    x("boo")
  }

  def methodB(x: => Unit): Unit = {
    x
  }

}
