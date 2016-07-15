package im.mange.shoreditch.example

import org.scalatest.{MustMatchers, WordSpec}
import scala.collection.concurrent.TrieMap

class ExampleSpec extends WordSpec with MustMatchers {
  private val shoreditch = Example.shoreditch

  "captures checks and actions" in {
    shoreditch.checks.size mustEqual 2
    shoreditch.checks.head mustEqual "base/check/successful/check" -> SuccessfulCheck

    shoreditch.actions mustEqual TrieMap(
      "base/action/successful/action" -> SuccessfulAction,
      "base/action/successful/action/with/return" -> SuccessfulActionWithReturn
    )
  }

  "handles missing requests" in {
    shoreditch.handle(SimpleRequest("")) mustEqual None
  }

  "handles check requests" in {
    val response = shoreditch.handle(SimpleRequest("base/check/successful/check"))
    response mustEqual Some("""{"failures":[]}""")
  }

  "handles action requests" in {
    val response = shoreditch.handle(SimpleRequest("base/action/successful/action"))
    response mustEqual Some("""{"failures":[]}""")
  }

  "handles metadata requests" in {
    val response = shoreditch.handle(SimpleRequest("base/metadata"))
    response mustEqual
      Some("""{"name":"Example System","alias":"example","version":"10001","checks":[{"url":"base/check/successful/check"},{"url":"base/check/successful/check/with/arg"}],"actions":[{"url":"base/action/successful/action","in":[]},{"url":"base/action/successful/action/with/return","in":[]}]}""")
  }

  "handles check requests with args" in {
    val response = shoreditch.handle(SimpleRequest("base/check/successful/check/with/args/arg"))
    response mustEqual Some("""{"failures":[]}""")
  }

  //BUG: this seems to run a check, maybe the first it finds?
  //TODO: making / be the same as /metadata might help ...
  "handles index requests" in {
    val response = shoreditch.handle(SimpleRequest("base"))
    response mustEqual Some("""{"failures":[]}""")
  }

  //TODO: handles check requests with params - using args
  //TODO: handles action requests with params
  //TODO: add failure cases ...
}


