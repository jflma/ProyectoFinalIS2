package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class LoginLoadSimulation extends Simulation {

    // Configuración del protocolo HTTP
    HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8081")
            .acceptHeader("application/json")
            .contentTypeHeader("application/json");

    // Escenario de login masivo
    ScenarioBuilder loginScenario = scenario("Login Masivo")
            .exec(
                    http("Login Request")
                            .post("/auth/login")
                            .body(StringBody("{\"username\": \"testuser\", \"password\": \"password123\"}"))
                            .check(status().in(200, 401)));

    // Configuración de carga: 10 usuarios por segundo durante 30 segundos
    {
        setUp(
                loginScenario.injectOpen(
                        constantUsersPerSec(10).during(30)))
                .protocols(httpProtocol)
                .assertions(
                        global().responseTime().max().lt(2000),
                        global().successfulRequests().percent().gt(95.0));
    }
}
