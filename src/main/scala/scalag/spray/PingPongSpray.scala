package scalag.spray

import akka.actor._
import akka.io.IO
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.Future
import scala.concurrent.duration._
import spray.can._
import spray.http._
import HttpMethods._

/**
 * // TODO: Document this
 * @author Galder Zamarreño
 * @since // TODO
 */
object PingPongSpray extends App {

   implicit val system = ActorSystem()

   // the handler actor replies to incoming HttpRequests
   val handler = system.actorOf(Props[Echo], name = "handler")

   IO(Http) ! Http.Bind(handler, interface = "localhost", port = 8080)

   private implicit val timeout: Timeout = 5.seconds
   demoHostLevelApi("localhost")

   class Echo extends Actor {
      def receive = {
         // when a new connection comes in we register ourselves as the connection handler
         case _: Http.Connected => sender ! Http.Register(self)

         case HttpRequest(GET, Uri.Path("/ping"), _, _, _) =>
            sender ! HttpResponse(entity = "PONG")
      }
   }

   def demoHostLevelApi(host: String)(implicit system: ActorSystem): Future[ProductVersion] = {
      import system.dispatcher // execution context for future transformations below
      for {
         HostConnectorInfo(hostConnector, _) <- IO(Http) ? HostConnectorSetup(host, port = 8080)
         response <- hostConnector.ask(HttpRequest(GET, "/ping")).mapTo[HttpResponse]
         _ <- hostConnector ? Http.CloseAll
      } yield {
         println("Host-Level API: received %s response %s".format(
            response.status, response.entity.asString))
         response.header[HttpHeaders.Server].get.products.head
      }
   }

}
