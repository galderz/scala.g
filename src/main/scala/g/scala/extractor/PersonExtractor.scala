package g.scala.extractor

object PersonExtractor extends App {

  val people: Seq[Person] = List(
    Person("Galder", List(Residence("Algorta"), Residence("Basel"), Residence("Ibiza"))),
    Person("Asier", List(Residence("Algorta"))))

//  val Algorta = StringSeqContains("España")
  val Algorta = StringSeqContains

  private val collected = people.collect {
    case LivesIn(Algorta) => println("hello")
    // case person @ Algorta() => person
  }

  println(collected)
//  println(LivesIn.unapply(people.head))
//  println(StringSeqContains.unapply(LivesIn.unapply(people.head).get))

  object LivesIn {
    def unapply(p: Person): Option[Seq[String]] =
      Some(
        for (r <- p.residences)
        yield r.city
      )
  }

//  class StringSeqContains(value: String) {
//    def unapply(in: Seq[String]): Boolean = {
//      println(in)
//      in.contains(value)
//    }
//  }


  object StringSeqContains {
    def unapply(in: Seq[String]): Boolean = {
      println(in)
      in.contains("Algorta")
    }
  }

  case class Person(name: String, residences: Seq[Residence])
  case class Residence(city: String)
}
