package service

import actor.TrackActor
import actor.TrackActor.{AddTrackActorRequest, TrackActorRequest}
import akka.actor.{ActorRef, ActorSystem}
import akka.http.scaladsl.server.{Directives, RequestContext, RouteResult}
import akka.pattern.ask
import akka.util.Timeout
import request.AddTrackRequest

import scala.concurrent.Promise
import scala.concurrent.duration._

class TrackService()(implicit system: ActorSystem) extends Directives {
  implicit val timeout = Timeout(5.seconds)
  val route            = trackByIdRoute

  def trackByIdRoute =
    path("v1" / "track" / Segment) { trackId: String =>
      get { ctx =>
        performRoute(ctx)(getActorRef)((actorRef) => {
          (actorRef ? TrackActorRequest(trackId))
        })
      }

    }

  def newTrack = path("v1" / "track" / "add") {
    entity(as[AddTrackRequest]) { request =>
      post { ctx =>
        performRoute(ctx)(getActorRef)((actorRef) => {
          (actorRef ? AddTrackActorRequest(request))
        })
      }
    }
  }

  def getActorRef(p: Promise[RouteResult], ctx: RequestContext): ActorRef = {
    system.actorOf(TrackActor.props(ctx, p))
  }

  def performRoute(ctx: RequestContext)(actorRef: (Promise[RouteResult], RequestContext) => ActorRef)(
      callback: (ActorRef) => Unit) = {
    val p = Promise[RouteResult]
    callback(actorRef(p, ctx))
    p.future
  }
}
