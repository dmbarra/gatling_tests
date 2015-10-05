//package computerdatabase // 1

import io.gatling.core.Predef._ // 2
import io.gatling.http.Predef._
import scala.concurrent.duration._

class TutorialGatling extends Simulation { // 3

  val httpConf = http // 4
    .baseURL("http://computer-database.herokuapp.com") // 5
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8") // 6
    .doNotTrackHeader("1")
    .acceptLanguageHeader("en-US,en;q=0.5")
    .acceptEncodingHeader("gzip, deflate")
    .userAgentHeader("Mozilla/5.0 (Windows NT 5.1; rv:31.0) Gecko/20100101 Firefox/31.0")

  val scn = scenario("BasicSimulation") // 7
    .exec(http("request_1")  // 8
      .get("/")) // 9
    .pause(5) // 10

  setUp( // 11
    scn.inject(atOnceUsers(1)) // 12
  ).protocols(httpConf) // 13
}


// What does it mean?

// 1- The optional package.
// 2 - The required imports.
// 3 - The class declaration. Note that it extends Simulation.
// 4 - The common configuration to all HTTP requests.
// Note
// val is the keyword for defining a constant value. Types are not defined and are inferred by the Scala compiler.

// 5 - The baseURL that will be prepended to all relative urls.
// 6 - Common HTTP headers that will be sent with all the requests.
// 7 - The scenario definition.
// 8 - A HTTP request, named request_1. This name will be displayed in the final reports.
// 9 - The url this request targets with the GET method.
// 10 - Some pause/think time.
// Note
// Duration units default to seconds, e.g. pause(5) is equivalent to pause(5 seconds).

// 11 - Where one sets up the scenarios that will be launched in this Simulation.
// 12 - Declaring to inject into scenario named scn one single user.
// 13 - Attaching the HTTP configuration declared above.
