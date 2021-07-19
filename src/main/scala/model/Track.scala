package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol
import utils.formatter.DateJsonFormat

case class Track(
    `type`: String,
    id: String,
    urn: String,
    titles: List[Title],
    availability: Availability,
)

object Track extends DefaultJsonProtocol with SprayJsonSupport with DateJsonFormat {
  implicit val availabilityFormat = jsonFormat3(Availability.apply)
  implicit val titleFor           = jsonFormat3(Title.apply)
  implicit val format             = jsonFormat5(Track.apply)
}
