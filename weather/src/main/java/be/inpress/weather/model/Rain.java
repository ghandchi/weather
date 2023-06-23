package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date & Time: 2023-06-22 14:50
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record Rain(
        @JsonProperty("1h")
        String lastOneHour,
        @JsonProperty("3h")
        String lastThreeHours) {
}
