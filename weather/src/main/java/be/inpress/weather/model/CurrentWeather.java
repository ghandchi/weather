package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * Date & Time: 2023-06-22 14:34
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record CurrentWeather(
        @JsonProperty("coord")
        Coordinate coordinate,
        @JsonProperty("weather")
        List<Weather> weatherList,
        Main main,
        Integer visibility,
        Wind wind,
        Rain rain) { // Some items have been omitted for the sake of brevity
}
