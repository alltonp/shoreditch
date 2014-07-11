package im.mange.shoreditch.api

/**
 * Created by pall on 12/07/2014.
 */
trait Action {
  def run: ActionResponse
  def success(returnValue: String) = ActionResponse(Nil, Some(returnValue))
  def failure(failures: List[String]) = ActionResponse(failures, None)
}
