package im.mange.shoreditch.handler

import im.mange.shoreditch.Shoreditch
import im.mange.shoreditch.api._
import im.mange.shoreditch.handler.HttpMethodPartialFunctions._

import scala.collection.concurrent

class ShoreditchHandler(shoreditch: Shoreditch) {
  var actions = concurrent.TrieMap[String, Action]()
  var checks = concurrent.TrieMap[String, Check]()

  shoreditch.routes.foreach(r =>
    r.service match {
      case a:Action => actions.update(shoreditch.base + "/" + r.pathStr, a)
      case c:Check => checks.update(shoreditch.base + "/" + r.pathStr, c)
      case x => //???
    })

  def handler(req: Request) : Option[ShoreditchResponse] =
    firstMatchingRoute(req).map(xform(req)) orElse summaryHandler(req)

  type ShoreditchResponse = () ⇒ String

  private val basePathParts = Route.splitPath(shoreditch.base)
  private val rebasedRoutes: Seq[Route[Service]] = shoreditch.routes.map { _ withBase basePathParts }

  //TODO: two things in here might explain the bogus GET listings we get ...
  private def summaryHandler(req: Request): Option[ShoreditchResponse] = {
    //TODO: not sure about this check actually ... it's stupid, remove it ...
    val summary = "metadata"
    if (summary.isEmpty) None
    else {
      val summaryResponse: ShoreditchResponse = () => {
        val theActions = actions.map(a => ActionMetaData(a._1, a._2.parameters.in, a._2.parameters.out)).toList
        val theChecks = checks.map(c => CheckMetaData(c._1)).toList

        val metaData = MetaDataResponse(shoreditch.longName, shoreditch.alias, shoreditch.version, theChecks, theActions)
        Json.serialise(metaData)
      }
      val summaryRoute: Route[ShoreditchResponse] = GET0(summary) {
        summaryResponse
      } withBase basePathParts
      summaryRoute.attemptMatch(req)
    }
  }

  private val matchers: Seq[(Request) => Option[Service]] = rebasedRoutes map { r ⇒ r.attemptMatch _ }

  private def lazyAppliedMatches(req: Request) = matchers.iterator map { _(req) }
  private def firstMatchingRoute(req: Request) = lazyAppliedMatches(req).find(_.isDefined).flatten
  private def xform(req: Request): (Service) => () => String = mkRunFunc(_, req)

  private def mkRunFunc(t: Service, req: Request): () ⇒ String = () ⇒ {
    t match {
      case a:Action if shoreditch.actionsEnabled ⇒ Json.serialise(Runner.run(a, req))
      case c:Check if shoreditch.checksEnabled ⇒ Json.serialise(Runner.run(c))
      case x => throw new RuntimeException("I don't know how to run a: " + x)
    }
  }
}