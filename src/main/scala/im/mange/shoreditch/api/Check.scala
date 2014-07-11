package im.mange.shoreditch.api

trait Check {
  def run: CheckResponse
  def success = CheckResponse(Nil)
  def failure(failures: List[String]) = CheckResponse(failures)
}
