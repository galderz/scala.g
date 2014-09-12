package scalag.error

import scala.util.{Success, Failure, Try}

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object BasicTryExample {

   def main(args : Array[String]) {
      val sumTry = for {
         int1 <- Try(Integer.parseInt("1"))
         int2 <- Try(Integer.parseInt("2"))
      } yield {
         int1 + int2
      }

//      println(sumTry)

      val errorSumTry = for {
         int1 <- Try(Integer.parseInt("a"))
         int2 <- Try(Integer.parseInt("2"))
      } yield {
         int1 + int2
      }

//      println(errorSumTry)


     def toInt(num: String): Try[Int] = {
       //Success(num.toInt)
       Try {
         num.toInt
       }
     }

     toInt("aaa").recover {
       case _ => println("Unexpected")
     }

     Try("aaa".toInt).recover {
       case _ => println("Unexpected")
     }

//     Try(throw new NoClassDefFoundError("booo")).recover {
//       case _ => println("Unexpected")
//     }

     classloadingTry(throw new NoClassDefFoundError("booo")).recover {
       case _ => println("Unexpected")
     }

     def classloadingTry[T](r: => T): Try[T] =
       try Success(r) catch {
         case e: Throwable => Failure(e)
       }

     //     Try(Failure(throw new NoClassDefFoundError("boo"))).recover {
//       case n: NoClassDefFoundError => print("saved!")
//       case _ => println("Something else")
//     }

//     Try {
//        throw new NoClassDefFoundError("boo")
//      }.recover {
//        case n: NoClassDefFoundError =>
//          print("saved!")
//      }
   }

}
