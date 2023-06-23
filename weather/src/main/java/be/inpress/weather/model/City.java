package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date & Time: 2023-06-22 16:20
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record City(String id,
                   String name,
                   @JsonProperty("coord")
                   Coordinate coordinate,
                   String country,
                   Long population,
                   String timezone) {
}
