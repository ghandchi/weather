package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

/**
 * Date & Time: 2023-06-23 00:27
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record AirPollutionRequest(
        String city,
        @JsonProperty("start-date")
        String startDate,
        @JsonProperty("end-date")
        String endDate) {
}
