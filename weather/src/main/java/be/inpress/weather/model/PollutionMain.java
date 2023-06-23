package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Date & Time: 2023-06-22 18:12
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record PollutionMain(@JsonProperty("aqi") AirQuality airQuality) {
}
