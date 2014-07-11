package im.mange.shoreditch

case class ActionResponse(failures: List[String], returnValue: Option[String])
case class CheckResponse(failures: List[String])
