package response

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class AddTrackResponse(message: String)
object AddTrackResponse extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val jsonFormat = jsonFormat1(AddTrackResponse.apply)
}
