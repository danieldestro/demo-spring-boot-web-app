package demo.app.web.config.security;

import static demo.app.sso.security.RestApiAuthenticationEntryPoint.DEFAULT_REST_API_ENTRYPOINT;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.userdetails.UserDetailsByNameServiceWrapper;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

import demo.app.sso.exception.SecurityConfigException;
import demo.app.sso.security.RestApiAuthenticationEntryPoint;
import demo.app.sso.service.UserAccountService;
import demo.app.web.security.SecurityUtils;
import demo.app.web.security.SsoHeaderFilter;

@EnableWebSecurity
@Profile({ "itg", "pro" })
public class UidSecurityConfig extends WebSecurityConfigurerAdapter {

    @Order(1)
    @Configuration
    @Profile({ "itg", "pro" })
    public static class RestSecurityConfig extends BaseSecurityConfig {

        private static final Logger             LOG = LoggerFactory.getLogger(RestSecurityConfig.class);

        @Autowired
        private RestApiAuthenticationEntryPoint restApiAuthenticationEntryPoint;

        @Override
        public void configure(HttpSecurity http) throws SecurityConfigException {

            LOG.info("Enabling Rest API Security for UID profile");

            try {
                csrf(http);

                // @formatter:off
                // REST API requests must be authenticated and may login via basic authentication
                http.antMatcher(DEFAULT_REST_API_ENTRYPOINT)
                .authorizeRequests().anyRequest().authenticated()
                //.and().httpBasic()
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
    @Profile({ "itg", "pro" })
    public static class WebSecurityConfig extends BaseSecurityConfig {

        private static final Logger LOG                 = LoggerFactory.getLogger(WebSecurityConfig.class);

        private static final String NOT_AUTHORIZED_PAGE = "/not-authorized.html";

        @Autowired
        private UserAccountService  userAccountService;

        @Override
        public void configure(AuthenticationManagerBuilder auth) {
            AuthenticationProvider provider = preAuthenticationAuthenticationProvider();
            auth.authenticationProvider(provider);
            LOG.info("AuthenticationManagerBuilder: {} configured for UID profile", provider.getClass().getSimpleName());
        }

        @Override
        public void configure(HttpSecurity http) throws SecurityConfigException {

            LOG.info("Enabling Web Security for UID profile");

            // restUnauthorizedEntryPoint.setLoginUrl(NOT_AUTHORIZED_PAGE);
            // restAccessDeniedHandler.setAccessDeniedUrl(NOT_AUTHORIZED_PAGE);

            try {
                csrf(http);

                // @formatter:off
                ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry conf = http.authorizeRequests();
                conf.antMatchers(NOT_AUTHORIZED_PAGE).permitAll();
               
                conf
//                    .antMatchers("/**").authenticated()
                    .anyRequest().authenticated()
                .and()
                    .addFilterBefore(ssoFilter(), RequestHeaderAuthenticationFilter.class)
                
//                .exceptionHandling()
//                    .authenticationEntryPoint(restUnauthorizedEntryPoint)
//                    .accessDeniedHandler(restAccessDeniedHandler)
//                    .and()

                    .logout()
                        .logoutRequestMatcher(path("/logout"))
                        .deleteCookies("JSESSIONID")
                        .deleteCookies(SecurityUtils.HEADER_NAME_X_REQUESTED_WITH)
                        .permitAll()
                .and()
                    .headers().frameOptions().sameOrigin();
                // @formatter:on

            } catch (SecurityConfigException ex) {
                LOG.error("SecurityConfigException:", ex);
                throw ex;
            } catch (Exception ex) {
                LOG.error("SecurityConfigException:", ex);
                throw new SecurityConfigException(ex);
            }
        }

        // @Bean
        public PreAuthenticatedAuthenticationProvider preAuthenticationAuthenticationProvider() {
            PreAuthenticatedAuthenticationProvider preauthAuthProvider = new PreAuthenticatedAuthenticationProvider();
            preauthAuthProvider.setPreAuthenticatedUserDetailsService(userDetailsServiceWrapper());
            return preauthAuthProvider;
        }

        // @Bean
        public UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> userDetailsServiceWrapper() {
            UserDetailsByNameServiceWrapper<PreAuthenticatedAuthenticationToken> wrapper = new UserDetailsByNameServiceWrapper<>();
            wrapper.setUserDetailsService(userAccountService);
            return wrapper;
        }

        // @Bean
        public SsoHeaderFilter ssoFilter() throws SecurityConfigException {
            SsoHeaderFilter filter = new SsoHeaderFilter();
            // filter.setIgnoreUris(IGNORE_URIS);
            try {
                filter.setAuthenticationManager(authenticationManager());
            } catch (Exception ex) {
                LOG.error("SsoHeaderFilter Exception:", ex);
                throw new SecurityConfigException(ex);
            }
            return filter;
        }
    }
}
