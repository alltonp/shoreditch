package im.mange.shoreditch

import im.mange.shoreditch.api.Service

trait Action extends Service {
  val parameters: Parameters = Parameters(Nil, None)
  def run(in: Seq[In]): ActionResponse
  def success(returnValue: Option[String]) = ActionResponse(Nil, returnValue)
  def failure(failures: Seq[String]) = ActionResponse(failures, None)
}

case class Parameters(in: Seq[In], out: Option[Out])
case class In(name: String, value: Option[String], validValues: Seq[String] = Nil)
case class Out(name: String)
