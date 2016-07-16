package im.mange.shoreditch.check

import im.mange.shoreditch.Check

case object Alive extends Check {
  override def run = success
}
