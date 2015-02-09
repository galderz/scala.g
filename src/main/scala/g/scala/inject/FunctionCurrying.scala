package g.scala.inject

/**
 * Dependency injection example using function
 * currying extracted from 'Lift in Action' book.
 */
object FunctionCurrying {

   def main(args : Array[String]) {
      import ExampleBookService._
      // The caller only has to supply the required parameter to service a
      // response. If the repository is replaced or altered at a later date,
      // none of the calling code has to change.
      val book = lookup("1234")
      println(book)
   }
}

class Book

trait BookRepository {
   def lookup(isbn: String): Option[Book]
   def add(book: Book)
}

trait BookService {
   val lookupBook: BookRepository => String => Option[Book] =
      repository => isbn => repository.lookup(isbn)
   val addBook: BookRepository => Book => Unit =
      repository => book => repository.add(book)
}

class DefaultBookRepository extends BookRepository {
   def lookup(isbn: String): Option[Book] = None
   def add(book: Book) = {}
}

object ExampleBookService extends BookService {
   val lookup = lookupBook(new DefaultBookRepository)
   val add = addBook(new DefaultBookRepository)
}