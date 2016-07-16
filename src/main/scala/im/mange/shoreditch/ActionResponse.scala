package im.mange.shoreditch

import im.mange.shoreditch.api.ServiceResponse

case class ActionResponse(failures: List[String], returnValue: Option[String]) extends ServiceResponse