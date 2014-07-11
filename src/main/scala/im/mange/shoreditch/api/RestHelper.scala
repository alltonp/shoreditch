package im.mange.shoreditch.api

import net.liftweb.common.Box
import net.liftweb.http.{GetRequest, LiftResponse, PostRequest, Req}

trait RestHelper extends net.liftweb.http.rest.RestHelper {
  def runAction(a: ⇒ Action): () ⇒ Box[LiftResponse] = { () ⇒ Runner.run(a) }
  def runCheck(c: ⇒ Check): () ⇒ Box[LiftResponse] = { () ⇒ Runner.run(c) }

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
