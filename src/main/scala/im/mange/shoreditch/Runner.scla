package im.mange.shoreditch

import net.liftweb.http._
import net.liftweb.common.Box
import net.liftweb.json.{JsonParser, NoTypeHints, Serialization}
import net.liftweb.json.Serialization._
import net.liftweb.common.Full

object Runner {
  def run(a: Action) = {
    val r = try { a.run }
    catch { case e: Throwable ⇒ a.failure(List(e.getMessage)) }
    println("### Action: " + a + " => " + r )
    Full(JsonResponse(Json.serialise(r)))
  }

  def run(c: Check) = {
    val r = try { c.run }
    catch { case e: Throwable ⇒ c.failure(List(e.getMessage)) }
    println("### Check: " + c + " => " + r )
    Full(JsonResponse(Json.serialise(r)))
  }
}
