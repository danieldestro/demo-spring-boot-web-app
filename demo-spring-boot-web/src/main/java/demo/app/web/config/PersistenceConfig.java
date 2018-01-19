package demo.app.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import demo.app.core.domain.EntityAuditorAwareImpl;

@Configuration
@EnableTransactionManagement
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
@EnableJpaRepositories(basePackages = { "demo.app", "demo.app.sso" })
public class PersistenceConfig {

    @Bean
    AuditorAware<String> auditorProvider() {
        return new EntityAuditorAwareImpl();
    }
}
