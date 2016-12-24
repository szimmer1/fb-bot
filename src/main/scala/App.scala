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

    // env vars
    val port: Int = sys.env.get("PORT").getOrElse("8080").toInt
    val verify_token: Option[String] = sys.env.get("PAGE_ACCESS_TOKEN")
    println(s"Got verify_token $verify_token")

    val route: Route =
      path("verify-fb") {
        get {
          parameters("hub.verify_token", "hub.challenge") { (token, challenge) =>
            if (! verify_token.isEmpty && verify_token == token) {
              complete(challenge)
            } else {
              complete(403, "Forbidden: Verify page token failed")
            }
          }
        }
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
