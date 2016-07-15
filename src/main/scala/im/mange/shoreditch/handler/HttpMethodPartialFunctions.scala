package im.mange.shoreditch.handler

object HttpMethodPartialFunctions {
  def POST[Service](pathstr: String)(fn: PartialFunction[List[String],Service]) = Route("POST", pathstr, fn)
  def POST0[Service](pathstr: String)(fn: ⇒ Service) = POST(pathstr){ case Nil ⇒ fn }
  def OPTIONS[Service](pathstr: String)(fn: PartialFunction[List[String],Service]) = Route("OPTIONS", pathstr, fn)
  def OPTIONS0[Service](pathstr: String)(fn: ⇒ Service) = OPTIONS(pathstr){case Nil ⇒ fn}
  def GET[Service](pathstr: String)(fn: PartialFunction[List[String],Service]) = Route("GET", pathstr, fn)
  def GET0[Service](pathstr: String)(fn: ⇒ Service) = GET(pathstr){ case Nil ⇒ fn }
  def GET1[Service](pathstr: String)(fn: String ⇒ Service) = GET(pathstr){ case List(x) ⇒ fn(x) }
  def GET2[Service](pathstr: String)(fn: (String,String) ⇒ Service) = GET(pathstr){ case List(x1,x2) ⇒ fn(x1,x2) }
}
