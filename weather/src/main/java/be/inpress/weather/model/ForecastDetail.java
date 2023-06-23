package be.inpress.weather.model;

import be.inpress.weather.util.EpochToDateTimeConverter;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;

import java.util.Date;
import java.util.List;

/**
 * Date & Time: 2023-06-22 16:11
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record ForecastDetail(
        @JsonProperty("dt")
        @JsonDeserialize(converter = EpochToDateTimeConverter.class)
        Date dateTime,
        Main main,
        @JsonProperty("weather")
        List<Weather> weatherList,
        Wind wind,
        String visibility,
        @JsonProperty("pop")
        Double precipitation,
        Rain rain,
        @JsonProperty("dt_txt")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        Date forecastedDate) {  // Some items have been omitted for the sake of brevity
}
