package im.mange.shoreditch

import im.mange.shoreditch.api._
import im.mange.shoreditch.handler.HttpMethodPartialFunctions._
import im.mange.shoreditch.handler.{Request, Route, ShoreditchHandler}

case class Shoreditch(base: String = "shoreditch",
                      version: String,
                      longName: String,
                      alias: String,
                      checksEnabled: Boolean = true,
                      actionsEnabled: Boolean = true,
                      debug: Boolean = false,
                      routes: Seq[Route[Service]]) {

  private val handler = new ShoreditchHandler(this)

  def handle(request: Request) = handler.handler(request).map(_())

  val actions = handler.actions
  val checks = handler.checks

  if (debug) println(
    s"""\nShoreditch: /$base => $longName ($alias) V$version, checksEnabled: $checksEnabled, actionsEnabled: $actionsEnabled
     | (${checks.size}) checks:\n${describe(checks.toSeq)}
     | (${actions.size}) actions:\n${describe(actions.toSeq)}
     """.stripMargin
  )

  private def describe(x: Seq[(String, Service)]) = x.map(c => s"  - ${c._1 + " -> " + c._2}").mkString("\n")
}

object Shoreditch {
  implicit class CheckRouteBuildingString(val path: String) extends AnyVal {
    def action(f:                ⇒ Action): Route[Service] = POST0("action/" + path)(f)
    def check(f:                 ⇒ Check): Route[Service]  = GET0("check/" + path)(f)
    def check(f: (String)        ⇒ Check): Route[Service]  = GET1("check/" + path)(f)
    def check(f: (String,String) ⇒ Check): Route[Service]  = GET2("check/" + path)(f)
  }
}
