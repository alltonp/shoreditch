package im.mange.shoreditch.api

import im.mange.shoreditch.{ActionResponse, CheckResponse, In}
import org.json4s._
import org.json4s.native.Serialization._

object Json {
  private val theFormats = formats(NoTypeHints)

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
