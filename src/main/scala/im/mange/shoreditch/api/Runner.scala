package im.mange.shoreditch.api

import net.liftweb.common.Full
import net.liftweb.http._

//TODO: kill println's or use a real logger (like reprobate)
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
