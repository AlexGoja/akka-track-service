import spray.json._
import DefaultJsonProtocol._
import model.{Availability, Title, Track}
import org.joda.time.format.DateTimeFormat
import org.scalatest.funsuite.AnyFunSuite

class JsonSupport extends AnyFunSuite {
  test("jsonTest") {
    val dateFormat = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")

    val availability = Availability(dateFormat.parseDateTime("2019-02-13T11:03:05Z"),
      dateFormat.parseDateTime("2019-03-15T11:00:00Z"),
      "Available for 29 days")

    println(availability.toJson)

    val title = Title(primary = "AC/DC", secondary = "Highway to Hell", tortiary = None)

   println(title.toJson)

    val track = Track(`type` = "track",
      id = "nznx3r",
      urn = "urn:bbc:sounds:track:nznx3r",
      titles = List(title),
      availability = availability)

    println(track.toJson)
  }
}
