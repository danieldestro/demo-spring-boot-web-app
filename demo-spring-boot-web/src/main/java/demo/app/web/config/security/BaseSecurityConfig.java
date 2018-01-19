package demo.app.web.config.security;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import demo.app.sso.exception.SecurityConfigException;
import demo.app.sso.service.UserAccountService;
import demo.app.web.config.AppConfigProperties;
import demo.app.web.security.CsrfHeaderFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public abstract class BaseSecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger   LOG = LoggerFactory.getLogger(BaseSecurityConfig.class);

    @Autowired
    private ServletContext        webContext;

    @Autowired
    private UserAccountService    userAccountService;

    @Autowired
    protected AppConfigProperties config;

    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return CookieCsrfTokenRepository.withHttpOnlyFalse();
    }

    protected AntPathRequestMatcher path(String path) {
        return new AntPathRequestMatcher(path);
    }

    protected void csrf(HttpSecurity http) throws Exception {
        if (config.getSecurity().getCsrf().isDisabled()) {
            http.csrf().disable();
            LOG.info("CSRF disabled");
        } else {
            LOG.info("CSRF enabled");
        }
    }

    @Autowired
    private void configureGlobal(AuthenticationManagerBuilder auth) throws SecurityConfigException {
        try {
            LOG.info("configureGlobal - AuthenticationManagerBuilder.userDetailsService(userAccountService)");
            auth.userDetailsService(userAccountService);

        } catch (SecurityConfigException ex) {
            LOG.error(ex.getMessage());
            throw ex;

        } catch (Exception ex) {
            LOG.error(ex.getMessage());
            throw new SecurityConfigException(ex);
        }
    }

    /**
     * @deprecated
     * @return
     */
    @Deprecated
    protected CsrfHeaderFilter createCsrfHeaderFilter() {
        return new CsrfHeaderFilter(config.getSecurity().getCsrf().isSecure(), webContext.getContextPath());
    }
}
