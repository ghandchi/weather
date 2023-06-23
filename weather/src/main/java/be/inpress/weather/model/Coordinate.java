package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date & Time: 2023-06-22 13:17
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record Coordinate(
        @JsonProperty("lat")
        Double latitude,
        @JsonProperty("lon")
        Double longitude) {
}
