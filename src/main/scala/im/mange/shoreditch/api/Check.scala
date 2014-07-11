package im.mange.shoreditch.api

/**
 * Created by pall on 12/07/2014.
 */
trait Check {
  def run: CheckResponse
  def success = CheckResponse(Nil)
  def failure(failures: List[String]) = CheckResponse(failures)
}
