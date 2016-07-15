package im.mange.shoreditch.example

import im.mange.shoreditch.handler.Request

case class SimpleRequest(path: String, json: String = "") extends Request