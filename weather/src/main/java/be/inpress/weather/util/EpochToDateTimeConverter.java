package be.inpress.weather.util;

import com.fasterxml.jackson.databind.util.StdConverter;

import java.util.Date;

/**
 * Date & Time: 2023-06-22 17:21
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public class EpochToDateTimeConverter extends StdConverter<String, Date> {
    @Override
    public Date convert(String epoch) {
        return new Date(Integer.parseInt(epoch) * 1_000L);
    }
}
