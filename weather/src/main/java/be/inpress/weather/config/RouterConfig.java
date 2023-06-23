package be.inpress.weather.config;

import be.inpress.weather.handler.WeatherHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

/**
 * Date & Time: 2023-06-22 12:46
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routes(WeatherHandler weatherHandler) {

        return route().path("/weather", builder -> builder
                        .GET("/current/{city}", accept(MediaType.APPLICATION_JSON), weatherHandler::getCurrentWeather)
                        .GET("/forecast/{city}", accept(MediaType.APPLICATION_JSON), weatherHandler::getForecast)
                        .POST("/air-pollution", accept(MediaType.APPLICATION_JSON), weatherHandler::getAirPollution)
                )
                .build();
    }
}
