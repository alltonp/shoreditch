package im.mange.shoreditch.check

import im.mange.shoreditch.api.Check

case object Alive extends Check {
  override def run = success
}
