package be.inpress.weather.service;

import be.inpress.weather.exception.CommunicationException;
import be.inpress.weather.model.AirPollution;
import be.inpress.weather.model.CurrentWeather;
import be.inpress.weather.model.Forecast;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * Date & Time: 2023-06-22 14:27
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class WeatherService {

    private final WebClient weatherWebClient;
    private final GeoService geoService;
    @Value("${application.weather.appid}")
    private String weatherAppid;

    public Mono<CurrentWeather> getCurrentWeather(String city) {
        return geoService.getCoordinate(city)
                .flatMap(coordinate -> weatherWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/data/2.5/weather")
                                .queryParam("lat", coordinate.latitude())
                                .queryParam("lon", coordinate.longitude())
                                .queryParam("units", "metric")
                                .queryParam("appid", weatherAppid)
                                .build())
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, httpResponse ->
                                Mono.error(new CommunicationException("Can not connect to the Weather API server; "
                                        + httpResponse.statusCode())))
                        .bodyToMono(CurrentWeather.class)
                        .onErrorResume(exception -> {
                            log.error("at getting current weather", exception);
                            return Mono.error(exception);
                        }))
                .doOnNext(currentWeather -> log.info("{}", currentWeather));
    }

    public Mono<Forecast> getForecast(String city) {
        return geoService.getCoordinate(city)
                .flatMap(coordinate -> weatherWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/data/2.5/forecast")
                                .queryParam("lat", coordinate.latitude())
                                .queryParam("lon", coordinate.longitude())
                                .queryParam("appid", weatherAppid)
                                .build())
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, httpResponse ->
                                Mono.error(new CommunicationException("Can not connect to the Weather API server; "
                                        + httpResponse.statusCode())))
                        .bodyToMono(Forecast.class)
                        .onErrorResume(exception -> {
                            log.error("at getting forecast", exception);
                            return Mono.error(exception);
                        })
                )
                .doOnNext(forecast -> log.info("{}", forecast));
    }

    public Mono<AirPollution> getAirPollution(String city, Long startDate, Long endDate) {
        return geoService.getCoordinate(city)
                .flatMap(coordinate -> weatherWebClient.get()
                        .uri(uriBuilder -> uriBuilder
                                .path("/data/2.5/air_pollution/history")
                                .queryParam("lat", coordinate.latitude())
                                .queryParam("lon", coordinate.longitude())
                                .queryParam("start", startDate)
                                .queryParam("end", endDate)
                                .queryParam("appid", weatherAppid)
                                .build())
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, httpResponse ->
                                Mono.error(new CommunicationException("Can not connect to the Weather API server; "
                                        + httpResponse.statusCode())))
                        .bodyToMono(AirPollution.class)
                        .onErrorResume(exception -> {
                            log.error("at getting air pollution", exception);
                            return Mono.error(exception);
                        })
                )
                .doOnNext(airPollution -> log.info("{}", airPollution));
    }
}
