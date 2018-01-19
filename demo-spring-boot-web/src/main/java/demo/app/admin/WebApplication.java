package demo.app.admin;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "demo.app", "demo.app.sso", "demo.app.commons" })
@EntityScan(basePackages = { "demo.app", "demo.app.sso" })
public class WebApplication extends SpringBootServletInitializer {

    private static final Logger LOG = LoggerFactory.getLogger(WebApplication.class);

    public static void main(String[] args) {

        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);

        if (LOG.isInfoEnabled()) {
            LOG.info("Active profiles: {}", Arrays.toString(ctx.getEnvironment().getActiveProfiles()));
        }

        logBeans(ctx);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(WebApplication.class);
    }

    private static void logBeans(ApplicationContext ctx) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Listing all beans:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOG.debug(beanName);
            }
            LOG.debug("Beans have been listed");
        }
    }
}