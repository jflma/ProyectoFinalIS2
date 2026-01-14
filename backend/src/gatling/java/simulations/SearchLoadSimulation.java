package simulations;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;
import java.util.List;
import java.util.Map;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

public class SearchLoadSimulation extends Simulation {

        // Configuración del protocolo HTTP
        HttpProtocolBuilder httpProtocol = http
                        .baseUrl("http://localhost:8081")
                        .acceptHeader("application/json");

        // Feeder con términos de búsqueda diversos
        FeederBuilder<String> searchTerms = listFeeder(
                        List.of(
                                        Map.of("query", "java"),
                                        Map.of("query", "spring"),
                                        Map.of("query", "error"),
                                        Map.of("query", "ayuda"),
                                        Map.of("query", "problema")))
                        .circular();

        // Escenario de búsqueda
        ScenarioBuilder searchScenario = scenario("Búsqueda de Posts")
                        .feed(searchTerms)
                        .exec(
                                        http("Search Posts")
                                                        .get("/post/search/#{query}")
                                                        .check(status().is(200)))
                        .pause(2, 5);

        // Prueba de estrés: carga progresiva hasta saturación
        {
                setUp(
                                searchScenario.injectOpen(
                                                rampUsersPerSec(5).to(50).during(60),
                                                constantUsersPerSec(50).during(30)))
                                .protocols(httpProtocol)
                                .assertions(
                                                global().responseTime().percentile(90.0).lt(2000),
                                                global().failedRequests().percent().lt(5.0));
        }
}
