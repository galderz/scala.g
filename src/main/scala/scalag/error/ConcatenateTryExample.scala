package scalag.error

import java.net.{MalformedURLException, URL}
import util.{Failure, Success, Try}
import io.Source
import java.io.FileNotFoundException

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ConcatenateTryExample {

   def parseURL(url: String): Try[URL] = Try(new URL(url))

   def getURLContent(url: String): Try[Iterator[String]] =
      for {
         url <- parseURL(url)
         connection <- Try(url.openConnection())
         is <- Try(connection.getInputStream)
         source = Source.fromInputStream(is)
      } yield source.getLines()

   def main(args : Array[String]) {
      getURLContent("http://danielwestheide.com/foobar") match {
         case Success(lines) => lines.foreach(println)
         case Failure(ex) => println(s"Problem rendering URL content: ${ex.getMessage}")
      }

      val content = getURLContent("garbage") recover {
         case e: FileNotFoundException => Iterator("Requested page does not exist")
         case e: MalformedURLException => Iterator("Please make sure to enter a valid URL")
         case _ => Iterator("An unexpected error has occurred. We are so sorry!")
      }
      content.get.foreach(println)
   }

}
