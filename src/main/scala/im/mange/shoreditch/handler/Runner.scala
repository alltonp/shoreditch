package im.mange.shoreditch.handler

import im.mange.shoreditch.api.{Action, Check, Json}

object Runner {
  import net.liftweb.json._

  def run(a: Action, req: Request) =
    try { a.run(Json.deserialiseIn(parse(req.json))) }
    catch { case e: Throwable ⇒ a.failure(List(e.getMessage)) }

  def run(c: Check) =
    try { c.run }
    catch { case e: Throwable ⇒ c.failure(List(e.getMessage)) }
}