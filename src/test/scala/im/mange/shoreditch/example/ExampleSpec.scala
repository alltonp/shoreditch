package im.mange.shoreditch.example

import org.json4s.native.JsonMethods._
import org.scalatest.{MustMatchers, WordSpec}

class ExampleSpec extends WordSpec with MustMatchers {
  private val shoreditch = Example.shoreditch
  private val success = Some("""{"failures":[]}""")
  private def failure(reason: String) = Some(s"""{"failures":["$reason"]}""")

  "captures checks and actions" in {
    shoreditch.checks.size mustEqual 3
    shoreditch.checks must contain ("base/check/successful/check" -> SuccessfulCheck)

    shoreditch.actions.size mustEqual 4
    shoreditch.actions must contain ("base/action/successful/action" -> SuccessfulAction)
  }

  "handles missing requests" in {
    shoreditch.handle(SimpleRequest("")) mustEqual None
  }

  "handles check requests" in {
    shoreditch.handle(SimpleRequest("base/check/successful/check")) mustEqual success
  }

  "handles check requests with failures" in {
    shoreditch.handle(SimpleRequest("base/check/failure/check")) mustEqual failure("Failed")
  }

  "handles action requests" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action")) mustEqual success
  }

  "handles action requests with failures" in {
    shoreditch.handle(SimpleRequest("base/action/failure/action")) mustEqual failure("Failed")
  }

  "handles metadata requests" in {
    val response = shoreditch.handle(SimpleRequest("base/metadata"))
    val expected = Some(compact(render(parse(
      """{"name":"Example System","alias":"example","version":"10001",
        |"checks":[
        |{"url":"base/check/successful/check/with/arg/@arg"},
        |{"url":"base/check/failure/check"},
        |{"url":"base/check/successful/check"}
        |]
        |"actions":[
        |{"url":"base/action/successful/action/with/parameters","in":[{"name":"name","validValues":[]}]},
        |{"url":"base/action/successful/action","in":[]},
        |{"url":"base/action/failure/action","in":[]},
        |{"url":"base/action/successful/action/with/return","in":[]}
        |]}""".stripMargin
    ))))

    println(s"$response\n$expected")

    response mustEqual expected
  }

  //TODO: we should check the arg
  "handles check requests with arg" in {
    shoreditch.handle(SimpleRequest("base/check/successful/check/with/arg/arg")) mustEqual success
  }

  //TODO: hmm this should blow up really ... how can In be resolved ???
  //seems like the despatching is not what it seems ...
  //so "successful/action/" is picking this up not us ...
  "handles action requests with params and empty json" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action/with/parameters", json = "")) mustEqual failure("Failed")
  }

  "handles action requests with params and invalid json" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action/with/parameters", json = "bd")) mustEqual
      Some("""{"failures":["unknown token b\nNear: b"]}""")
  }

  "handles index requests" in {
    shoreditch.handle(SimpleRequest("base")) mustEqual None
  }

  "rejects checks with bogus endings to valid service" in {
    shoreditch.handle(SimpleRequest("base/check/successful/check/bogus")) mustEqual None
  }

  "rejects actions with bogus endings to valid service" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action/bogus")) mustEqual None
  }

  //TODO: handles action requests with params
  //TODO: handles action requests with return values
  //TODO: add failure cases ... no json on actions, bad json etc
  //TODO: ensure that actions are always posts and checks are always gets ...
  //TODO: ensure keys cannot be duplicated
}


