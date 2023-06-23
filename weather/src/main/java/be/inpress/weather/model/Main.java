package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date & Time: 2023-06-22 14:42
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record Main(
        @JsonProperty("temp")
        Double temperature,
        Integer pressure,
        Integer humidity,
        @JsonProperty("temp_min")
        Double minimumTemperature,
        @JsonProperty("temp_max")
        Double maximumTemperature) {
}
