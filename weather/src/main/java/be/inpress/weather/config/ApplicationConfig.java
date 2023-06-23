package be.inpress.weather.config;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Date & Time: 2023-06-22 12:19
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@Slf4j
@Configuration
public class ApplicationConfig {

    @Bean
    public WebClient getWeatherWebClient(WebClient.Builder webClientBuilder, Environment environment) {

        log.info("Building Weather WebClient bean...");

        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, environment.getRequiredProperty("application.weather.connection-timeout", int.class))
                .responseTimeout(Duration.ofMillis(environment.getRequiredProperty("application.weather.response-timeout", long.class)))
                .doOnConnected(conn ->
                        conn.addHandlerLast(new ReadTimeoutHandler(environment.getRequiredProperty("application.weather.read-timeout", long.class), TimeUnit.MILLISECONDS))
                                .addHandlerLast(new WriteTimeoutHandler(environment.getRequiredProperty("application.weather.write-timeout", long.class), TimeUnit.MILLISECONDS)));

        return webClientBuilder.baseUrl(environment.getRequiredProperty("application.weather.base-url", String.class))
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
