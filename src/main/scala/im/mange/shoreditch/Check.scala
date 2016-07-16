package im.mange.shoreditch

import im.mange.shoreditch.api.Service

trait Check extends Service {
  def run: CheckResponse
  def success = CheckResponse(Nil)
  def failure(failures: List[String]) = CheckResponse(failures)
}
