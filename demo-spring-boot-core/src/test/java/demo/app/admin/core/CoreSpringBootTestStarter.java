package demo.app.admin.core;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * This Spring Boot starter is meant to be used for Casper Admin Core module tests only.
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = { "demo.app" })
@ComponentScan(basePackages = { "demo.app" })
@EntityScan(basePackages = { "demo.app" })
public class CoreSpringBootTestStarter {

}
