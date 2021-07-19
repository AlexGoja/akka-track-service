package request

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import model.{Availability, Title, Track}
import spray.json.DefaultJsonProtocol
import utils.formatter.DateJsonFormat

case class AddTrackRequest(
    track: Track
)

object AddTrackRequest extends SprayJsonSupport with DefaultJsonProtocol with DateJsonFormat {
  implicit val availabilityFormat = jsonFormat3(Availability.apply)
  implicit val titleFor           = jsonFormat3(Title.apply)
  implicit val trackFormat        = jsonFormat5(Track.apply)
  implicit val track              = jsonFormat1(AddTrackRequest.apply)
}
