package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

/**
 * Date & Time: 2023-06-22 17:57
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record AirPollution(
        @JsonProperty("coord")
        Coordinate coordinate,
        List<PollutionDetail> list) {
}
