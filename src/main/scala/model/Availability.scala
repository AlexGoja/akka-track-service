package model
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.DateTime
import spray.json.DefaultJsonProtocol
import utils.formatter.DateJsonFormat

case class Availability(
    from: DateTime,
    to: DateTime,
    label: String
)

object Availability extends DefaultJsonProtocol with SprayJsonSupport with DateJsonFormat {
  implicit val format = jsonFormat3(Availability.apply)
}
