package actor

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Status}
import akka.http.scaladsl.marshalling.ToResponseMarshallable
import akka.http.scaladsl.model.StatusCodes.InternalServerError
import akka.http.scaladsl.server.{RequestContext, RouteResult}
import exceptions.MediaException
import model.{Availability, Title, Track}
import org.joda.time.format.DateTimeFormat
import request.AddTrackRequest
import response.{AddTrackResponse, TrackErrorResponse}
import scala.concurrent.{Future, Promise}

object TrackActor {
  def props(ctx: RequestContext, p: Promise[RouteResult]): Props =
    Props(new TrackActor(ctx, p))
  case class TrackActorRequest(trackId: String)
  case class TrackActorResponse(response: Either[MediaException, Track])
  case class AddTrackActorRequest(trackRequest: AddTrackRequest)
  case class AddTrackActorResponse(response: Either[MediaException, String])
}

class TrackActor(ctx: RequestContext, p: Promise[RouteResult]) extends Actor with ActorLogging {

  import actor.TrackActor._
  import context._
  import akka.pattern.pipe

  override def receive: Receive = {
    case TrackActorRequest(trackId) =>
      val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
      val trackOrError = if (trackId.equals("nznx3r")) {
        val availability = Availability(dateFormat.parseDateTime("2019-02-13T11:03:05Z"),
                                        dateFormat.parseDateTime("2019-03-15T11:00:00Z"),
                                        "Available for 29 days")
        val title = Title(primary = "AC/DC", secondary = "Highway to Hell", tortiary = None)
        val track = Track(`type` = "track",
                          id = "nznx3r",
                          urn = "urn:bbc:sounds:track:".concat(trackId),
                          titles = List(title),
                          availability = availability)
        Right(track)
      } else {
        Left(MediaException(s"Track with id: $trackId"))
      }
      Future(TrackActorResponse(trackOrError)) pipeTo self
      context.become(done(sender))

    case AddTrackActorRequest(trackRequest) =>
      val response = if (trackRequest.track.id.equals("nznx3r")) {
        Left(MediaException(s"Track already exists with id: ${trackRequest.track.id}"))
      } else {
        Right(s"Track added successfully!")
      }
      Future(AddTrackActorResponse(response)) pipeTo self
      context.become(done(sender))
  }

  def done(originalSender: ActorRef): Receive = {

    case TrackActorResponse(trackResponse) => {
      trackResponse match {
        case Left(e)      => complete(TrackErrorResponse(e.toString))
        case Right(track) => complete(track)
      }
    }

    case AddTrackActorResponse(addTrackResp) => {
      addTrackResp match {
        case Left(e)        => complete(TrackErrorResponse(e.toString))
        case Right(success) => complete(AddTrackResponse(success))
      }
    }

    case Status.Failure(ex) =>
      println(ex)
      log.debug(ex.toString)
      complete(InternalServerError, "failed")
  }

  def complete(m: => ToResponseMarshallable): Unit = {
    val f = ctx.complete(m)
    f.onComplete(p.complete)
    stop(self)
  }

  override def preStart(): Unit = println("TrackActor actor {}-{} started")

  override def postStop(): Unit = {
    super.postStop()
    println("Track actor {}-{} stopped")
  }
}
