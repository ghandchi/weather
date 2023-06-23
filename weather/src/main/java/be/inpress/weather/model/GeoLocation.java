package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * Date & Time: 2023-06-22 12:14
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record GeoLocation(String name,
                          @JsonProperty("local_names")
                          Map<String, String> localNames,
                          @JsonProperty("lat")
                          Double latitude,
                          @JsonProperty("lon")
                          Double longitude,
                          String country,
                          String state) {
    public String localName(String language) {

        if(language == null || CollectionUtils.isEmpty(localNames)){
            return null;
        }

        return localNames.get(language.toLowerCase());
    }
}
