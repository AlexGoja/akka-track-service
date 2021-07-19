package utils.formatter

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import org.joda.time.DateTime
import org.joda.time.format.{DateTimeFormatter, ISODateTimeFormat}
import spray.json.{DefaultJsonProtocol, DeserializationException, JsString, JsValue, RootJsonFormat}

trait DateJsonFormat extends SprayJsonSupport with DefaultJsonProtocol {

  implicit object DateJsonFormat extends RootJsonFormat[DateTime] {

    private val parserISO: DateTimeFormatter = ISODateTimeFormat.dateTimeNoMillis()

    override def write(obj: DateTime) = JsString(parserISO.print(obj))

    override def read(json: JsValue): DateTime = json match {
      case JsString(s) => parserISO.parseDateTime(s)
      case _           => throw DeserializationException("Invalid date format: " + json)
    }
  }

}
