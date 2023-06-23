package be.inpress.weather.util;

import be.inpress.weather.exception.InvalidParameterException;
import org.springframework.util.StringUtils;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;

/**
 * Date & Time: 2023-06-23 10:43
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public interface Validator {

    static void assertNotEmpty(String value, String fieldName) {

        if (!StringUtils.hasLength(value)) {
            throw new InvalidParameterException("The field '" + fieldName + "' can not be null, empty or omitted.");
        }
    }

    static void assertValidDateTime(String dateTimeStr, String pattern, String fieldName) {

        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            dateFormatter.parse(dateTimeStr);
        } catch (DateTimeParseException e) {
            throw new InvalidParameterException("The date '" + fieldName + "' is invalid, the pattern '"
                    + pattern + "' should be observed.", e);
        }
    }
}
