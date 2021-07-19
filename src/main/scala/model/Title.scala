package model

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json.DefaultJsonProtocol

case class Title(
    primary: String,
    secondary: String,
    tortiary: Option[String]
)

object Title extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val format = jsonFormat3(Title.apply)
}
