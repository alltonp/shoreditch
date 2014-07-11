package im.mange.shoreditch.api

trait Action {
  def run: ActionResponse
  def success(returnValue: String) = ActionResponse(Nil, Some(returnValue))
  def failure(failures: List[String]) = ActionResponse(failures, None)
}
