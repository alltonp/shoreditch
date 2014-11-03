package im.mange.shoreditch.api

case class CheckMetaData(url: String)
case class ActionMetaData(url: String, in: List[In], out: Option[Out]) //TODO: Seq
case class MetaDataResponse(version: String, checks: Seq[CheckMetaData], actions: Seq[ActionMetaData])