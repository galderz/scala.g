package scalazg.learning.day18

import scalaz._
import Scalaz._

/**
 * @author Galder Zamarreño
 */
object Application extends App {

  import CharToy._

  val subroutine = output('A')
  val program = for {
    _ <- subroutine
    _ <- bell
    _ <- done
  } yield ()

  // S $ program
  print(showProgram(program))

}
