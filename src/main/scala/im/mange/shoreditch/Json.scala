package im.mange.shoreditch

import net.liftweb.json.{JsonParser, NoTypeHints, Serialization}
import net.liftweb.json.Serialization._

object Json {
  private val shoreditchFormats = Serialization.formats(NoTypeHints)
  
  def serialise(r: ActionResponse) = {
    implicit val formats = shoreditchFormats
    JsonParser.parse(write(r))
  }

  def serialise(r: CheckResponse) = {
    implicit val formats = shoreditchFormats
    JsonParser.parse(write(r))
  }
}