package scalag.variance

/**
 * @author Galder Zamarreño
 */
object VarianceDangers extends App {

  println(List(1, 2, 3).toSet()) // Returns false!
  println(List(1, 2, 3) contains "your mum") // Returns false!

}
