package im.mange.shoreditch.handler

import im.mange.shoreditch.{Action, Check}
import im.mange.shoreditch.api.Json

object Runner {
  import org.json4s._
  import org.json4s.native.JsonMethods._

  def run(a: Action, req: Request) =
    try { a.run(Json.deserialiseIn(parse(req.json))) }
    catch { case e: Throwable ⇒ a.failure(Seq(e.getMessage)) }

  def run(c: Check) =
    try { c.run }
    catch { case e: Throwable ⇒ c.failure(Seq(e.getMessage)) }
}