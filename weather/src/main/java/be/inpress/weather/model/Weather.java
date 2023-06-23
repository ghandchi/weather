package be.inpress.weather.model;

import lombok.Builder;

/**
 * Date & Time: 2023-06-22 14:39
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Builder
public record Weather(
        String id,
        String main,
        String description) {
}
