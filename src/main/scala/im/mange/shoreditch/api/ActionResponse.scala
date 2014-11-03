package im.mange.shoreditch.api

case class ActionResponse(failures: List[String], returnValue: Option[String]) extends ServiceResponse