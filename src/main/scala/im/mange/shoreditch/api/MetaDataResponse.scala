package im.mange.shoreditch.api

import im.mange.shoreditch.{In, Out}

case class CheckMetaData(url: String)
case class ActionMetaData(url: String, in: Seq[In], out: Option[Out])
case class MetaDataResponse(name: String, alias: String, version: String, checks: Seq[CheckMetaData], actions: Seq[ActionMetaData])