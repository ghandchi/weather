package be.inpress.weather.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Date & Time: 2023-06-22 18:08
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public enum AirQuality {
    GOOD, FAIR, MODERATE, POOR, VERY_POOR;

    @JsonCreator
    public static AirQuality getByCode(String code) {

        return switch (code) {
            case "1" -> GOOD;
            case "2" -> FAIR;
            case "3" -> MODERATE;
            case "4" -> POOR;
            case "5" -> VERY_POOR;
            default -> null;
        };
    }

    @JsonValue
    public int toValue() {
        return ordinal() + 1;
    }
}
