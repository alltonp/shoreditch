package im.mange.shoreditch.example

import org.json4s.native.JsonMethods._
import org.scalatest.{MustMatchers, WordSpec}

class ExampleSpec extends WordSpec with MustMatchers {
  private val shoreditch = Example.shoreditch
  private val success = Some("""{"failures":[]}""")
  private val failure = Some("""{"failures":["Failed"]}""")

  "captures checks and actions" in {
    shoreditch.checks.size mustEqual 3
    shoreditch.checks must contain ("base/check/successful/check" -> SuccessfulCheck)

    shoreditch.actions.size mustEqual 3
    shoreditch.actions must contain ("base/action/successful/action" -> SuccessfulAction)
  }

  "handles missing requests" in {
    shoreditch.handle(SimpleRequest("")) mustEqual None
  }

  "handles check requests" in {
    shoreditch.handle(SimpleRequest("base/check/successful/check")) mustEqual success
  }

  "handles check requests with failures" in {
    shoreditch.handle(SimpleRequest("base/check/failure/check")) mustEqual failure
  }

  "handles action requests" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action")) mustEqual success
  }

  "handles action requests with failures" in {
    shoreditch.handle(SimpleRequest("base/action/failure/action")) mustEqual failure
  }

  "handles metadata requests" in {
    val response = shoreditch.handle(SimpleRequest("base/metadata"))
    val expected = Some(compact(render(parse(
      """{"name":"Example System","alias":"example","version":"10001",
        |"checks":[
        |{"url":"base/check/failure/check"},
        |{"url":"base/check/successful/check"},
        |{"url":"base/check/successful/check/with/@arg"}],
        |"actions":[
        |{"url":"base/action/successful/action","in":[]},
        |{"url":"base/action/failure/action","in":[]},
        |{"url":"base/action/successful/action/with/return","in":[]}]}""".stripMargin
    ))))

//    println(response)
//    println(expected)

    response mustEqual expected
  }

  "handles check requests with args" in {
    shoreditch.handle(SimpleRequest("base/check/successful/check/with/args/arg")) mustEqual success
  }

  "handles action requests with args" in {
    shoreditch.handle(SimpleRequest("base/action/successful/action/with/args", json = "")) mustEqual success
  }

  //BUG: this seems to run a check, maybe the first it finds?
  "handles index requests" in {
    pending
    shoreditch.handle(SimpleRequest("base")) mustEqual None
  }

  //BUG: this seems to run a check, maybe the first it finds?
  "rejects checks with bogus endings to valid service" in {
    pending
    shoreditch.handle(SimpleRequest("base/check/successful/check/bogus")) mustEqual None
  }

  //BUG: this seems to run an action, maybe the first it finds?
  "rejects actions with bogus endings to valid service" in {
    pending
    shoreditch.handle(SimpleRequest("base/action/successful/action/bogus")) mustEqual None
  }

  //TODO: handles action requests with params
  //TODO: handles action requests with return values
  //TODO: add failure cases ... no json on actions, bad json etc
  //TODO: ensure that actions are always posts and checks are always gets ...
  //TODO: ensure keys cannot be duplicated
}


