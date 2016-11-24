package wiring

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.ws.ahc.AhcWSClient
import services.BadgeBuilder

/**
  * Just a container that holds various module we need for the web app
  */
class Modules {

  // Just use defaults for now
  implicit val actorSystem = ActorSystem()
  implicit val materializer = ActorMaterializer()
  implicit val executionContext = scala.concurrent.ExecutionContext.global

  // Our web client
  val wsClient = AhcWSClient()

  val badgeBuilder = new BadgeBuilder(wsClient)

}
