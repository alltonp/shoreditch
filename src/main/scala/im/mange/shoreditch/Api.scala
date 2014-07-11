package im.mange.shoreditch

case class ActionResponse(failures: List[String], returnValue: Option[String])
case class CheckResponse(failures: List[String])

trait Action {
  def run: ActionResponse
  def success(returnValue: String) = ActionResponse(Nil, Some(returnValue))
  def failure(failures: List[String]) = ActionResponse(failures, None)
}

trait Check {
  def run: CheckResponse
  def success = CheckResponse(Nil)
  def failure(failures: List[String]) = CheckResponse(failures)
}
