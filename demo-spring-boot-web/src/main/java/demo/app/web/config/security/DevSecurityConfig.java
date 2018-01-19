package demo.app.web.config.security;

import static demo.app.sso.security.RestApiAuthenticationEntryPoint.DEFAULT_REST_API_ENTRYPOINT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import demo.app.sso.exception.SecurityConfigException;
import demo.app.sso.security.RestApiAuthenticationEntryPoint;
import demo.app.web.security.SecurityUtils;

@EnableWebSecurity
@Profile({ "dev" })
public class DevSecurityConfig extends WebSecurityConfigurerAdapter {

    public static class DevBaseSecurityConfig extends BaseSecurityConfig {
        @Override
        protected void csrf(HttpSecurity http) throws Exception {
            super.csrf(http);
            if (!config.getSecurity().getCsrf().isDisabled()) {
                http.csrf().csrfTokenRepository(csrfTokenRepository());
            }
        }
    }

    @Order(1)
    @Configuration
    @Profile({ "dev" })
    public static class RestSecurityConfig extends DevBaseSecurityConfig {

        private static final Logger             LOG = LoggerFactory.getLogger(RestSecurityConfig.class);

        @Autowired
        private RestApiAuthenticationEntryPoint restApiAuthenticationEntryPoint;

        @Override
        public void configure(HttpSecurity http) throws SecurityConfigException {

            LOG.info("Enabling Rest API Security");

            try {
                csrf(http);

                // @formatter:off
                // REST API requests must be authenticated and may login via basic authentication
                http.antMatcher(DEFAULT_REST_API_ENTRYPOINT)
                .authorizeRequests().anyRequest().authenticated()
                .and()
                    .httpBasic()
                .and()
                    .headers().frameOptions().sameOrigin()
                .and()
                    .exceptionHandling()
                        .defaultAuthenticationEntryPointFor(restApiAuthenticationEntryPoint, path(DEFAULT_REST_API_ENTRYPOINT));
                // @formatter:on

            } catch (SecurityConfigException ex) {
                LOG.error(ex.getMessage());
                throw ex;

            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                throw new SecurityConfigException(ex);
            }
        }
    }

    @Order(2)
    @Configuration
    @Profile({ "dev" })
    public static class WebSecurityConfig extends DevBaseSecurityConfig {

        private static final Logger LOG = LoggerFactory.getLogger(WebSecurityConfig.class);

        @Override
        public void configure(HttpSecurity http) throws SecurityConfigException {

            LOG.info("Enabling Web Security");

            try {
                csrf(http);

                // @formatter:off
                // All other web requests follow the configuration below
                http
                    .authorizeRequests()
                    .anyRequest().authenticated()
                .and()
                    .formLogin().permitAll()
                .and()
                    .logout()
                        .logoutRequestMatcher(path("/logout"))
                        .deleteCookies("JSESSIONID")
                        .deleteCookies(SecurityUtils.HEADER_NAME_X_REQUESTED_WITH)
                        .permitAll()
                .and()
                    .headers().frameOptions().sameOrigin();
                // @formatter:on

            } catch (SecurityConfigException ex) {
                LOG.error(ex.getMessage());
                throw ex;

            } catch (Exception ex) {
                LOG.error(ex.getMessage());
                throw new SecurityConfigException(ex);
            }
        }
    }
}
