package be.inpress.discovery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * Date & Time: 2022-07-17 11:01
 *
 * @author <a href="mailto:askar.ghandchi@gmail.com">Askar Ghandchi</a>
 * @version 1.0
 */
@SpringBootApplication
@EnableEurekaServer
public class Application {
    public static void main(String... args) {
        SpringApplication.run(Application.class, args);
    }
}
