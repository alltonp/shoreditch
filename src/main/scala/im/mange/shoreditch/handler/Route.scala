package im.mange.shoreditch.handler

sealed trait PathPart { def simpleString: String }
case class StaticPathPart(str: String) extends PathPart { def simpleString = str }
case class DynPathPart(name: String) extends PathPart { def simpleString = "@" + name }

object Route {
  def apply[Service](requestMethod: String, path: String, fn: PartialFunction[List[String], Service]): Route[Service] = {
    new Route[Service](requestMethod, splitPath(path), fn)
  }

  def splitPath(str: String): List[PathPart] = {
    val pathParts: Array[PathPart] = str split '/' map {
      case x if x startsWith "@" ⇒ DynPathPart(x.tail)
      case x ⇒ StaticPathPart(x)
    }
    pathParts.toList
  }
}

class Route[Service] private (requestMethod: String, pathParts: List[PathPart], fn: PartialFunction[List[String], Service]) {
  lazy val pathStr = pathParts.map(_.simpleString).mkString("/")

  //TODO: this is nasty - this should have an escape after too many attempts...
  val service = {
    var attempt: List[String] = Nil
    while (!fn.isDefinedAt(attempt)) {
      attempt = "?" :: attempt
    }
    fn.apply(attempt)
  }

  @annotation.tailrec
  private def recMatch(pairs: List[(PathPart,String)], acc: List[String] = Nil): Option[List[String]] =
    pairs match {
      case (StaticPathPart(exp), act) :: tail if exp == act ⇒ recMatch(tail, acc)
      case (StaticPathPart(exp), act) :: tail ⇒ None
      case (DynPathPart(_), act) :: tail ⇒ recMatch(tail, act :: acc)
      case Nil ⇒ Some(acc.reverse)
      case _ ⇒ ???
    }

  def attemptMatch(req: Request) : Option[Service] = {
    val pairs = pathParts zip req.path.split("/")
    val theMatch = recMatch(pairs)
    theMatch map attemptFn
  }

  private def attemptFn(xs: List[String]): Service =
    if (fn.isDefinedAt(xs)) { fn(xs) }
    else {
      throw new RuntimeException(s"The backing function for path $pathStr takes the wrong number of elements, but have: " + xs)
    }

  def withBase(base: List[PathPart]): Route[Service] = new Route(requestMethod, base ::: pathParts, fn)
}