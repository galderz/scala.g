package scalag.actors

import actors.{TIMEOUT, Actor}
import actors.Actor.actor
import actors.Actor.self
// import actors.Actor.actor

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object ChatRoomApp {

  def main(args : Array[String]) {
    val chatRoom = new ChatRoom
    chatRoom.start()
    // Asynchronous subscribe
    chatRoom ! Subscribe(User("galder"))
    // Synchronous subscribe
    chatRoom !? Subscribe(User("claire")) match {
      case response: String => println(response)
    }
    // With future
    val future = chatRoom !! Subscribe(User("asier"))
    println(future()) // Wait for future

    chatRoom ! UserPost(User("galder"), Post("Bazinga!"))
  }

}

class ChatRoom extends Actor {

  var session = Map.empty[User, Actor]

  def act() {
    while (true) {
      receive {
        case Subscribe(user) => // handle subscriptions
          println("'%s' joined the chat room".format(user.name))
          val subscriber = sender
          val sessionUser =
            actor {
              while(true) {
                // If no post received within 5 seconds, unsubscribe user
                self.receiveWithin(5 * 1000) {
                  case Post(msg) => // send message to sender
                    subscriber ! Post(msg)
                  case TIMEOUT =>
                    println("Timeout...")
                    this ! Unsubscribe(user)
                    exit()
                }
              }
            }
          session = session + (user -> sessionUser)
          reply("Subscribed " + user)
        case Unsubscribe(user) =>
          println("Unsuscribe " + user.name)
        case UserPost(user, post) => // handle user posts
          for (key <- session.keys; if key != user) {
            println("Send post '%s' to %s".format(post.msg, key))
            session(key) ! post
          }
      }
    }
  }
}

case class User(name: String)

case class Subscribe(user: User)

case class Unsubscribe(user: User)

case class Post(msg: String)

case class UserPost(user: User, post: Post)
