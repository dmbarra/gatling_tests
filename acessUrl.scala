
import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class acessUrl extends Simulation {

	var URL : String = "http://computer-database.herokuapp.com"

	val configurationHttp = http
		.baseURL(URL)
		.inferHtmlResources()

	val scenarioTestInicial = scenario("acessUrl")
		.exec(http("Load URL")
			.get("/computers")
			.resources(http("Get resources")
			.get(URL + "/favicon.ico")
			.check(status.is(404))))
		.pause(1)
		// Consultando
		.exec(http("Search macbook")
			.get("/computers?f=macbook"))

		// Consultando
		.exec(http("Search Daniel")
			.get("/computers?f=retina"))

	setUp(
		scenarioTestInicial.inject(
    		nothingFor(4 seconds), // 1
    		atOnceUsers(10), // 2
    		rampUsers(10) over(5 seconds), // 3
   		 	constantUsersPerSec(20) during(15 seconds), // 4
    		constantUsersPerSec(20) during(15 seconds) randomized, // 5
    		rampUsersPerSec(10) to(20) during(1 minutes), // 6
    		rampUsersPerSec(10) to(20) during(1 minutes) randomized, // 7
    		splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(10 seconds), // 8
    		splitUsers(1000) into(rampUsers(10) over(10 seconds)) separatedBy(atOnceUsers(30)), // 9
    		heavisideUsers(1000) over(20 seconds) // 10
    ).protocols(configurationHttp))

}

// 1 - nothingFor(duration): Pause for a given duration.
// 2 - atOnceUsers(nbUsers): Injects a given number of users at once.
// 3 - rampUsers(nbUsers) over(duration): Injects a given number of users with a linear ramp over a given duration.
// 4 - constantUsersPerSec(rate) during(duration): Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
// 5 - constantUsersPerSec(rate) during(duration) randomized: Injects users at a constant rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
// 6 - rampUsersPerSec(rate1) to (rate2) during(duration): Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at regular intervals.
// 7 - rampUsersPerSec(rate1) to(rate2) during(duration) randomized: Injects users from starting rate to target rate, defined in users per second, during a given duration. Users will be injected at randomized intervals.
// 8 - heavisideUsers(nbUsers) over(duration): Injects a given number of users following a smooth approximation of the heaviside step function stretched to a given duration.
// 9 - splitUsers(nbUsers) into(injectionStep) separatedBy(duration): Repeatedly execute the defined injection step separated by a pause of the given duration until reaching nbUsers, the total number of users to inject.
// 10 - splitUsers(nbUsers) into(injectionStep1) separatedBy(injectionStep2): Repeatedly execute the first defined injection step (injectionStep1) separated by the execution of the second injection step (injectionStep2) until reaching nbUsers, the total number of users to inject.



