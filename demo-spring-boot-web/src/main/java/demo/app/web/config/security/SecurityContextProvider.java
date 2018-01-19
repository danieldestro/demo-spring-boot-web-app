package demo.app.web.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Bean factory that provides a Spring {@link SecurityContext}.
 */
@Configuration
public class SecurityContextProvider {

    @Bean
    public SecurityContext getSecurityContext() {

        return SecurityContextHolder.getContext();
    }
}
