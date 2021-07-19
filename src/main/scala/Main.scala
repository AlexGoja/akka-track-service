import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model.{HttpEntity, HttpResponse}
import akka.http.scaladsl.model.StatusCodes.Unauthorized
import akka.http.scaladsl.server.{MalformedRequestContentRejection, RejectionHandler}
import akka.stream.ActorMaterializer
import com.typesafe.config.ConfigFactory
import service.TrackService

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object Main extends App {

  implicit val system: ActorSystem             = ActorSystem(name = "bbc-sound-service")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  import system.dispatcher

  import akka.http.scaladsl.server.Directives._
  val routes = new TrackService().route

  implicit def rejectionHandler =
    RejectionHandler
      .newBuilder()
      .handle {
        case MalformedRequestContentRejection(x, e) => complete(HttpResponse(Unauthorized, entity = HttpEntity(x)))
      }
      .result()

  val host    = ConfigFactory.load().getString("host.ipaddress")
  val port    = 9000
  val binding = Http().bindAndHandle(routes, host, port)
  binding.onComplete {
    case Success(_)     => println(s"Server online and listening for requests at $host:$port")
    case Failure(error) => println(s"Server failed: ${error.getMessage}")
  }

  scala.sys.addShutdownHook {
    println("Terminating...")
    system.terminate()
    Await.result(system.whenTerminated, 30.seconds)
    println("Terminated... Bye")
  }

}
