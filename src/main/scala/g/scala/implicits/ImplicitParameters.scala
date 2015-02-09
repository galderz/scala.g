package g.scala.implicits

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ImplicitParameters extends App {

  class PreferredPrompt(val preference: String)

  object Greeter {
    def greet(name: String)(implicit prompt: PreferredPrompt) {
      println("Welcome, "+ name +". The system is ready.")
      println(prompt.preference)
    }
  }

  val bobsPrompt = new PreferredPrompt("relax> ")

  // Greeter.greet("Bob")(bobsPrompt)

  implicit val prompt = new PreferredPrompt("Yes, master> ")
  Greeter.greet("Bob")

}
