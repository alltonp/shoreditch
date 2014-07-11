package im.mange.shoreditch

import net.liftweb.http._
import net.liftweb.common.Box
import net.liftweb.json.{JsonParser, NoTypeHints, Serialization}
import net.liftweb.json.Serialization._
import net.liftweb.common.Full
import scala.Some

trait RestHelper extends net.liftweb.http.rest.RestHelper {
  def run(a: ⇒ Action): () ⇒ Box[LiftResponse] = { () ⇒ Runner.run(a) }
  def run(c: ⇒ Check): () ⇒ Box[LiftResponse] = { () ⇒ Runner.run(c) }

  object GET {
    def unapplySeq(in: Req): Option[Seq[String]] = in match {
      case Req(out, _, GetRequest) ⇒ Some(out)
      case _ ⇒ None
    }
  }

  object POST {
    def unapplySeq(in: Req): Option[Seq[String]] = in match {
      case Req(out, _, PostRequest) ⇒ Some(out)
      case _ ⇒ None
    }
  }
}
