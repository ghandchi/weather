package be.inpress.weather;

import be.inpress.weather.config.RouterConfig;
import be.inpress.weather.handler.WeatherHandler;
import be.inpress.weather.model.*;
import be.inpress.weather.service.WeatherService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import reactor.core.publisher.Mono;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

/**
 * Date & Time: 2023-05-09 12:00
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@ExtendWith(MockitoExtension.class)
public class EndpointWithoutContextTest {
    @Mock
    private WeatherService weatherService;
    private WebTestClient webTestClient;

    @BeforeEach
    public void setup() {
        WeatherHandler weatherHandler = new WeatherHandler(weatherService, "yyyy-MM-dd");
        RouterFunction<?> routes = new RouterConfig().routes(weatherHandler);
        webTestClient = WebTestClient.bindToRouterFunction(routes).build();
    }

    @Test
    public void givenCity_whenGetCurrentWeather_thenReturnCurrentWeather() {

        CurrentWeather expectedCurrentWeather = CurrentWeather.builder()
                .coordinate(new Coordinate(35.6893, 51.3896))
                .weatherList(List.of(new Weather("800", "Clear", "clear sky")))
                .main(new Main(32.9, 1012, 16, 32.73, 33.1))
                .visibility(10000)
                .wind(new Wind(2.06, 270))
                .build();

        // given
        given(weatherService.getCurrentWeather(any())).willReturn(Mono.just(expectedCurrentWeather));

        // when
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/weather/current").pathSegment("Tehran").build())
                .exchange()

                // then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.coord.lat").isEqualTo(expectedCurrentWeather.coordinate().latitude())
                .jsonPath("$.coord.lon").isEqualTo(expectedCurrentWeather.coordinate().longitude())
                .jsonPath("$.weather").isArray()
                .jsonPath("$.weather[0].id").isEqualTo(expectedCurrentWeather.weatherList().get(0).id())
                .jsonPath("$.weather[0].main").isEqualTo(expectedCurrentWeather.weatherList().get(0).main());
    }

    @Test
    public void givenCity_whenGetForecast_thenReturnForecast() {

        Forecast expectedForecast = buildAnExpectedForecastResponse();

        // given
        given(weatherService.getForecast(any())).willReturn(Mono.just(expectedForecast));

        // when
        webTestClient.get()
                .uri(uriBuilder -> uriBuilder.path("/weather/forecast").pathSegment("Paris").build())
                .exchange()

                // then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.cnt").isEqualTo(expectedForecast.count())
                .jsonPath("$.list[0].weather").isArray()
                .jsonPath("$.list[0].weather[0].main").isEqualTo(expectedForecast.list().get(0).weatherList().get(0).main())
                .jsonPath("$.list[0].main.pressure").isEqualTo(expectedForecast.list().get(0).main().pressure());
    }

    private Forecast buildAnExpectedForecastResponse() {

        ForecastDetail forecastDetail1 = ForecastDetail.builder()
                .dateTime(getDate("2023-06-23T12:00:00.000Z"))
                .weatherList(List.of(new Weather("801", "Clouds", "few clouds")))
                .precipitation(0d)
                .main(new Main(298.4, 1025, 54, 298.4, 299.26))
                .wind(new Wind(2.32, 289))
                .visibility("10000")
                .build();

        ForecastDetail forecastDetail2 = ForecastDetail.builder()
                .dateTime(getDate("2023-06-23T15:00:00.000Z"))
                .weatherList(List.of(new Weather("802", "Clouds", "scattered clouds")))
                .precipitation(0d)
                .main(new Main(298.4, 1025, 54, 298.4, 299.26))
                .wind(new Wind(2.53, 300))
                .visibility("10000")
                .build();

        return Forecast.builder()
                .count("2")
                .list(List.of(forecastDetail1, forecastDetail2))
                .city(new City("6545270", "Palais-Royal", new Coordinate(48.8589, 2.32), "FR", 2_233_002L, "7200"))
                .build();
    }

    @Test
    public void givenAirPollutionRequest_whenPostRequest_thenReturnAirPollution() {

        AirPollutionRequest airPollutionRequest =
                AirPollutionRequest.builder()
                        .city("Brussels")
                        .startDate("2023-06-20")
                        .endDate("2023-06-23")
                        .build();

        AirPollution expectedAirPollution = buildAnExpectedAirPollutionResponse();

        // given
        given(weatherService.getAirPollution(any(), any(), any())).willReturn(Mono.just(expectedAirPollution));

        // when
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/weather/air-pollution").build())
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(airPollutionRequest)
                .exchange()

                // then
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.coord.lat").isEqualTo(expectedAirPollution.coordinate().latitude())
                .jsonPath("$.coord.lon").isEqualTo(expectedAirPollution.coordinate().longitude())
                .jsonPath("$.list").isArray()
                .jsonPath("$.list[0].main.aqi").isEqualTo(expectedAirPollution.list().get(0).main().airQuality().toValue())
                .jsonPath("$.list[1].components.so2").isEqualTo(expectedAirPollution.list().get(1).components().so2());
    }

    private AirPollution buildAnExpectedAirPollutionResponse() {

        PollutionDetail pollutionDetail1 = PollutionDetail.builder()
                .dateTime(getDate("2023-06-20T00:00:00.000Z"))
                .main(new PollutionMain(AirQuality.MODERATE))
                .components(new PollutionComponents(380.52, 5.48, 79.51,
                        1.65, 4.77, 7.52, 8.95, 14.19))
                .build();

        PollutionDetail pollutionDetail2 = PollutionDetail.builder()
                .dateTime(getDate("2023-06-20T01:00:00.000Z"))
                .main(new PollutionMain(AirQuality.FAIR))
                .components(new PollutionComponents(283.72, 0.0, 23.31,
                        64.37, 2.21, 4.22, 4.78, 3.45))
                .build();

        return AirPollution.builder()
                .coordinate(new Coordinate(50.8466, 4.3517))
                .list(List.of(pollutionDetail1, pollutionDetail2))
                .build();
    }

    private Date getDate(String dateStr) {

        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").parse(dateStr);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
