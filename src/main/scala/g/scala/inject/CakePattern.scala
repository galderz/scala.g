package g.scala.inject

/**
 * Cake pattern in action for dependency injection.
 *
 * Just a note, if you compare the different strategies using this naive
 * example then the Cake Pattern might look a bit overly complicated with
 * its nested (namespace) traits, but it really starts to shine when you have
 * a less then trivial example with many components with more or less complex
 * dependencies to manage.
 *
 * More info in:
 * http://jonasboner.com/2008/10/06/real-world-scala-dependency-injection-di/
 *
 * More info on self-type annotations (anything clearer?):
 * http://www.scala-lang.org/node/124
 */
object CakePatternDependencyInjection {

   def main(args : Array[String]) {
      val userService = ComponentRegistry.userService
      println(userService.authenticate("galder", "password"))

      val mockUserService = TestingEnvironment.userService
      println(mockUserService.authenticate("galder", "password"))
   }

}

class User(username: String, password: String)

trait UserRepositoryComponent {

   val userRepository: UserRepository

   class UserRepository {
      def authenticate(user: User): User = {
         println("authenticating user: " + user)
         user
      }
      def create(user: User) = println("creating user: " + user)
      def delete(user: User) = println("deleting user: " + user)
   }

}

// using self-type annotation declaring the dependencies this
// component requires, in our case the UserRepositoryComponent
trait UserServiceComponent { this: UserRepositoryComponent =>

   val userService: UserService

   class UserService {
      def authenticate(username: String, password: String): User =
         userRepository.authenticate(new User(username, password))
      def create(username: String, password: String) =
         userRepository.create(new User(username, password))
      def delete(user: User) = userRepository.delete(user)
   }

}

trait MockUserRepositoryComponent {

   val userRepository: MockUserRepository

   class MockUserRepository {
      def authenticate(user: User): User = {
         println("[mock] authenticating user: " + user)
         null
      }
      def create(user: User) = println("[mock] creating user: " + user)
      def delete(user: User) = println("[mock] deleting user: " + user)
   }

}


trait MockUserServiceComponent { this: MockUserRepositoryComponent =>

   val userService: MockUserService

   class MockUserService {
      def authenticate(username: String, password: String): User =
         userRepository.authenticate(new User(username, password))
      def create(username: String, password: String) =
         userRepository.create(new User(username, password))
      def delete(user: User) = userRepository.delete(user)
   }

}

object ComponentRegistry
        extends UserServiceComponent
                with UserRepositoryComponent {
   val userRepository = new UserRepository
   val userService = new UserService
}

object TestingEnvironment
        extends MockUserServiceComponent
        with MockUserRepositoryComponent {
   val userRepository = new MockUserRepository
   val userService = new MockUserService
}
