package fbbot

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import scala.io.StdIn

object App {
  def main(argv: Array[String]) {
    implicit val system = ActorSystem("msg-reciever")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher

    val route: Route =
      path("health") {
        get {
          complete(StatusCodes.OK)
        }
      }
    
    val port = sys.env.get("PORT") match {
      case Some(port_str) => port_str.toInt
      case None => 8001
    }

    val interface = "0.0.0.0"
    val bindingFuture = Http().bindAndHandle(route, interface=interface, port=port)

    sys.addShutdownHook({
      println("Shutting down server");
      bindingFuture
        .flatMap(binding => binding.unbind())
        .onComplete(_ => system.terminate())
    })

    println(s"Server running on interface http://$interface:$port")
  }
}
