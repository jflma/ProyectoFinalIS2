package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class PostReadLoadSimulation extends Simulation {

  // Configuración del protocolo HTTP
  HttpProtocolBuilder httpProtocol = http
      .baseUrl("http://localhost:8080")
      .acceptHeader("application/json");

  // Escenario de lectura de posts
  ScenarioBuilder postReadScenario = scenario("Lectura de Posts")
      .exec(
          http("Get Recent Posts")
              .get("/post/getUltimatePosts")
              .check(status().is(200)))
      .pause(1, 3);

  // Configuración de carga: escalamiento progresivo
  {
    setUp(
        postReadScenario.injectOpen(
            rampUsersPerSec(1).to(20).during(60)))
        .protocols(httpProtocol)
        .assertions(
            global().responseTime().mean().lt(500),
            global().responseTime().percentile(95.0).lt(1000),
            global().successfulRequests().percent().gte(99.0));
  }
}
