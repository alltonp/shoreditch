package im.mange.shoreditch

import im.mange.shoreditch.api.ServiceResponse

case class CheckResponse(failures: Seq[String]) extends ServiceResponse
