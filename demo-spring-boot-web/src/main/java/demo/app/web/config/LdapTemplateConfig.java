package demo.app.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;

import demo.app.web.config.AppConfigProperties.LdapConf;

@Configuration
public class LdapTemplateConfig {

    @Autowired
    private AppConfigProperties config;

    @Bean
    public LdapContextSource contextSource() {
        LdapConf ldapConf = config.getLdap();

        LdapContextSource contextSource = new LdapContextSource();
        contextSource.setAnonymousReadOnly(true);
        contextSource.setUrl(ldapConf.getUrl());
        contextSource.setBase(ldapConf.getBase());
        contextSource.setUserDn(ldapConf.getUser());
        contextSource.setPassword(ldapConf.getPassword());
        return contextSource;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        return new LdapTemplate(contextSource());
    }
}
