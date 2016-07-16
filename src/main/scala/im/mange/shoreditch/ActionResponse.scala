package im.mange.shoreditch

import im.mange.shoreditch.api.ServiceResponse

case class ActionResponse(failures: Seq[String], returnValue: Option[String]) extends ServiceResponse