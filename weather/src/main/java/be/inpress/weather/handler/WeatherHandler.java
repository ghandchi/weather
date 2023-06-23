package be.inpress.weather.handler;

import be.inpress.weather.model.AirPollution;
import be.inpress.weather.model.AirPollutionRequest;
import be.inpress.weather.service.WeatherService;
import be.inpress.weather.util.Validator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Date & Time: 2023-06-22 12:47
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Component
public class WeatherHandler {

    private final WeatherService weatherService;
    private final String dateTimePattern;

    public WeatherHandler(WeatherService weatherService, @Value("${application.date-time-pattern}") String dateTimePattern) {
        this.weatherService = weatherService;
        this.dateTimePattern = dateTimePattern;
    }

    public Mono<ServerResponse> getCurrentWeather(ServerRequest request) {

        String city = request.pathVariable("city");

        return weatherService.getCurrentWeather(city)
                .flatMap(currentWeather -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(currentWeather))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getForecast(ServerRequest request) {
        String city = request.pathVariable("city");
        return weatherService.getForecast(city)
                .flatMap(forecast -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(forecast))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getAirPollution(ServerRequest request) {

        return request.bodyToMono(AirPollutionRequest.class)
                .flatMap(airPollutionRequest -> {
                            validate(airPollutionRequest);
                            long unixStartTime = getUnixTime(airPollutionRequest.startDate());
                            long unixEndTime = getUnixTime(airPollutionRequest.endDate());
                            return ServerResponse.ok()
                                    .contentType(MediaType.APPLICATION_JSON)
                                    .body(weatherService.getAirPollution(airPollutionRequest.city(),
                                            unixStartTime, unixEndTime), AirPollution.class);
                        }
                );
    }

    // It is looked Currently Spring standard validation mechanism (such as @Valid and Validator interface)
    // has not been developed for Functional Routing REST services so this method do the task temporarily
    private void validate(AirPollutionRequest request) {

        Validator.assertNotEmpty(request.city(), "city");

        Validator.assertNotEmpty(request.city(), "start-date");
        Validator.assertValidDateTime(request.startDate(), dateTimePattern, "start-date");

        Validator.assertNotEmpty(request.city(), "end-date");
        Validator.assertValidDateTime(request.endDate(), dateTimePattern, "end-date");
    }

    // The Weather API uses unix epoch time and this method provide it
    private long getUnixTime(String dateTimeStr) {
        LocalDate date = LocalDate.parse(dateTimeStr, DateTimeFormatter.ofPattern(dateTimePattern));
        return date.atStartOfDay(ZoneOffset.UTC).toEpochSecond();
    }
}
