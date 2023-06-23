package be.inpress.weather.model;

import be.inpress.weather.util.EpochToDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.Date;

/**
 * Date & Time: 2023-06-22 18:00
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record PollutionDetail(
        @JsonProperty("dt")
        @JsonDeserialize(converter = EpochToDateTimeConverter.class)
        Date dateTime,
        PollutionMain main,
        PollutionComponents components) {
}
