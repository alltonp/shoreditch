package im.mange.shoreditch

import im.mange.shoreditch.api._
import im.mange.shoreditch.handler.HttpMethodPartialFunctions._
import im.mange.shoreditch.handler.{Request, Route, ShoreditchHandler}

//TODO: should be no api.liftweb deps in here
//TODO: make depends on json4s
//TODO: move Action/Check and any deps to im.mange.shoreditch package for ease of airport
case class Shoreditch(base: String = "shoreditch",
                      version: String,
                      longName: String,
                      alias: String,
                      checksEnabled: Boolean = true,
                      actionsEnabled: Boolean = true,
                      routes: Seq[Route[Service]]) {

  private val handler = new ShoreditchHandler(this)

  def handle(request: Request) = handler.handler(request).map(_())

  val actions = handler.actions
  val checks = handler.checks
}

object Shoreditch {
  implicit class CheckRouteBuildingString(val path: String) extends AnyVal {
    def action(f:                ⇒ Action): Route[Service] = POST0("action/" + path)(f)
    def check(f:                 ⇒ Check): Route[Service]  = GET0("check/" + path)(f)
    def check(f: (String)        ⇒ Check): Route[Service]  = GET1("check/" + path)(f)
    def check(f: (String,String) ⇒ Check): Route[Service]  = GET2("check/" + path)(f)
  }
}
