package be.inpress.weather.model;

/**
 * Date & Time: 2023-06-22 18:14
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
public record PollutionComponents(
        Double co,
        Double no,
        Double no2,
        Double o3,
        Double so2,
        Double pm2_5,
        Double pm10,
        Double nh3) {
}
