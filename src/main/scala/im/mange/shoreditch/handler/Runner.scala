package im.mange.shoreditch.handler

import im.mange.shoreditch.api.{Action, Check, Json}

object Runner {
  import org.json4s._
  import org.json4s.native.JsonMethods._

  def run(a: Action, req: Request) =
    try { a.run(Json.deserialiseIn(parse(req.json))) }
    catch { case e: Throwable ⇒ a.failure(List(e.getMessage)) }

  def run(c: Check) =
    try { c.run }
    catch { case e: Throwable ⇒ c.failure(List(e.getMessage)) }
}