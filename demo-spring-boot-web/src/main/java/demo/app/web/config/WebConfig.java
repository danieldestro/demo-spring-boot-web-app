package demo.app.web.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.ResourceUrlEncodingFilter;

// @Profile("!local")
@Component
public class WebConfig extends WebMvcConfigurerAdapter {

    private static final Logger LOG = LoggerFactory.getLogger(WebConfig.class);

    /**
     * Used when resource versioning with Spring MVC is active. <br>
     * <code>spring.resources.chain.strategy.content.enabled=true</code> <br>
     * Applies only to profiles specified with {@link Profile}.
     * 
     * @return
     */
    @Profile("!local")
    @Bean
    public ResourceUrlEncodingFilter resourceUrlEncodingFilter() {
        return new ResourceUrlEncodingFilter();
    }
}
