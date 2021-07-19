package response

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class TrackErrorResponse(error: String)
object TrackErrorResponse extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val format = jsonFormat1(TrackErrorResponse.apply)
}
