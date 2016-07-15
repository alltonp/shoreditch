package im.mange.shoreditch.example

import im.mange.shoreditch.Shoreditch
import im.mange.shoreditch.Shoreditch._
import im.mange.shoreditch.api._

object Example {
  val shoreditch = Shoreditch(
    base = "base",
    version = "10001",
    longName = "Example System",
    alias = "example",
    routes = Seq(
      "successful/check/" check SuccessfulCheck,
      "successful/check/with/arg" check SuccessfulCheckWithArg,
      "successful/action/" action SuccessfulAction,
      "successful/action/with/return" action SuccessfulActionWithReturn
    )
  )
}


case object SuccessfulCheck extends Check {
  override def run = success
}

case class SuccessfulCheckWithArg(arg: String) extends Check {
  override def run = success
}

case object SuccessfulAction extends Action {
  override def run(in: List[In]) = success(None)
}

case object SuccessfulActionWithReturn extends Action {
  override def run(in: List[In]) = success(Some("returnValue"))
}
