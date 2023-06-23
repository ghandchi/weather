package be.inpress.weather.service;

import be.inpress.weather.exception.CommunicationException;
import be.inpress.weather.exception.NotFoundException;
import be.inpress.weather.model.Coordinate;
import be.inpress.weather.model.GeoLocation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.NoSuchElementException;

/**
 * Date & Time: 2023-06-22 14:41
 *
 * The Open Weather Map API uses latitude and longitude to serve weather services.
 * There is a service in the API which returns these data using city name
 * This service has been intended to provide this service.
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class GeoService {

    private final WebClient weatherWebClient;
    @Value("${application.weather.appid}")
    private String weatherAppid;

    public Mono<Coordinate> getCoordinate(String cityName) {
        return weatherWebClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/geo/1.0/direct")
                        .queryParam("q", cityName)
                        .queryParam("limit", 1)
                        .queryParam("appid", weatherAppid)
                        .build())
                .retrieve()
                .onStatus(HttpStatusCode::isError, httpResponse ->
                        Mono.error(new CommunicationException("Can not connect to the Weather API server; "
                                + httpResponse.statusCode())))
                .bodyToFlux(GeoLocation.class)
                .last()
                .onErrorResume(exception -> {
                    log.error("at fetching coordinate of '{}'",cityName, exception);
                    if (exception instanceof NoSuchElementException)
                        return Mono.error(new NotFoundException("The city '" + cityName + "' not found"));
                    else
                        return Mono.error(exception);
                })
                .doOnNext(geoLocation -> log.info(geoLocation.toString()))
                .flatMap(geoLocation -> Mono.just(new Coordinate(geoLocation.latitude(), geoLocation.longitude())));
    }
}
