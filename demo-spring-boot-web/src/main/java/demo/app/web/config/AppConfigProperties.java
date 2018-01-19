package demo.app.web.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
@ConfigurationProperties(prefix = "demo")
public class AppConfigProperties {

    private static final Logger LOG      = LoggerFactory.getLogger(AppConfigProperties.class);

    private SecurityConf        security = new SecurityConf();

    private LdapConf            ldap     = new LdapConf();

    private String              appVersionNumber;
    private String              appBuildNumber;

    public SecurityConf getSecurity() {
        return security;
    }

    public LdapConf getLdap() {
        return ldap;
    }

    public String getAppVersionNumber() {
        return appVersionNumber;
    }

    public void setAppVersionNumber(String appVersionNumber) {
        this.appVersionNumber = appVersionNumber;
    }

    public String getAppBuildNumber() {
        return appBuildNumber;
    }

    public void setAppBuildNumber(String appBuildNumber) {
        this.appBuildNumber = appBuildNumber;
    }

    @PostConstruct
    public void info() {
        if (LOG.isInfoEnabled()) {
            LOG.info("[AppConfigProperties created]");
            LOG.info("app.version.number: {}", getAppVersionNumber());
            LOG.info("app.build.number: {}", getAppBuildNumber());
            LOG.info("security.csrf.disabled: {}", security.csrf.isDisabled());
            LOG.info("security.csrf.secure: {}", security.csrf.isSecure());
            LOG.info("ldap.url: {}", ldap.getUrl());
            LOG.info("ldap.base: {}", ldap.getBase());
            LOG.info("ldap.user: {}", ldap.getUser());
            LOG.info("ldap.password: {}", ldap.getPassword().replaceAll("[\\w\\W]", "*"));
        }
    }

    public static class SecurityConf {

        private CsrfConf csrf = new CsrfConf();

        public CsrfConf getCsrf() {
            return csrf;
        }
    }

    public static class CsrfConf {

        private boolean disabled;
        private boolean secure;

        public boolean isDisabled() {
            return disabled;
        }

        public void setDisabled(boolean disabled) {
            this.disabled = disabled;
        }

        public boolean isSecure() {
            return secure;
        }

        public void setSecure(boolean secure) {
            this.secure = secure;
        }
    }

    public static class LdapConf {

        private String url;
        private String base;
        private String user;
        private String password;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getBase() {
            return base;
        }

        public void setBase(String base) {
            this.base = base;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}
