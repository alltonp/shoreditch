package im.mange.shoreditch.api

import net.liftweb.json.Serialization._
import net.liftweb.json._

object Json {
  private val theFormats = Serialization.formats(NoTypeHints)

  def serialise(r: ActionResponse) = {
    implicit val formats = theFormats
    write(r)
  }

  def serialise(r: CheckResponse) = {
    implicit val formats = theFormats
    write(r)
  }

  def serialise(r: MetaDataResponse) = {
    implicit val formats = theFormats
    write(r)
  }

  def deserialiseIn(json: JValue) = {
    implicit val formats = theFormats
    json.extract[List[In]]
  }
}
