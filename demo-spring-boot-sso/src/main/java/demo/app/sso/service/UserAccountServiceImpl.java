package demo.app.sso.service;

import java.util.Optional;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.query.LdapQuery;
import org.springframework.ldap.query.LdapQueryBuilder;
import org.springframework.ldap.query.SearchScope;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import demo.app.core.data.BusinessException;
import demo.app.core.service.CrudServiceImpl;
import demo.app.core.util.MessageUtils;
import demo.app.sso.domain.UserAccount;
import demo.app.sso.repository.UserAccountRepository;

/**
 * Service to support the User Account APIs.
 */
@Service
@Transactional
public class UserAccountServiceImpl extends CrudServiceImpl<UserAccount, Integer> implements UserAccountService {

    @Autowired
    private UserAccountRepository repository;

    @Autowired
    private LdapTemplate          ldapTemplate;

    @Autowired
    private SecurityContext       context;

    @Autowired
    private MessageUtils          messageUtils;

    private static final Integer  TIMEOUT = 3000;                                                                                                                                                                                                     // in
                                                                                                                                                                                                                                                      // milliseconds

    private static final Logger   LOG     = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        LOG.debug("Attempt to log in with username '{}'", username);
        if (StringUtils.isBlank(username)) {
            String errmsg = messageUtils.getMessage("err.login.username.required");
            LOG.error(errmsg);
            throw new UsernameNotFoundException(errmsg);
        }

        UserAccount userAccount = repository.findByEmployeeNumber(username);
        if (userAccount == null) {
            String errmsg = messageUtils.getMessage("err.login.user.empnum.not.found", username);
            LOG.error(errmsg);
            throw new UsernameNotFoundException(errmsg);
        }

        LOG.info("Login successful with {} found user {} with authorities {}", username, userAccount.getEmailAddress(),
                userAccount.getAuthorities());

        return userAccount;
    }

    @Override
    public UserAccount getLoggedUserAccount() {
        Authentication authentication = context.getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return (UserAccount) authentication.getPrincipal();
    }

    @Override
    public boolean userHasRole(String roleName) {
        return Optional.ofNullable(getLoggedUserAccount()).map(userAccount -> userAccount.hasRole(roleName)).orElse(false);
    }

    @Override
    public boolean userHasAuthority(String authorityName) {
        return Optional.ofNullable(getLoggedUserAccount()).map(userAccount -> userAccount.hasAuthority(authorityName)).orElse(false);
    }

    @Override
    public Page<UserAccount> findByEmailAddressContaining(String email, Pageable page) {
        return repository.findByEmailAddressContaining(email, page);
    }

    @Override
    public String findEmployeeNumber(String email) {
        LdapQuery query = LdapQueryBuilder.query()
                .searchScope(SearchScope.SUBTREE)
                .timeLimit(TIMEOUT).countLimit(1)
                .attributes("employeeNumber")
                .base(LdapUtils.emptyLdapName())
                .where("objectclass").is("person")
                .and("mail").is(email.toLowerCase());

        // return employeeNumber
        return ldapTemplate
                .search(query, new EmployeeNumberAttributesMapper())
                .stream().findFirst().orElse(null);
    }

    @Override
    public UserAccount findByEmailAddress(String email) {
        return repository.findByEmailAddress(email);
    }

    @Override
    public UserAccount create(UserAccount entity) {
        validateEmailNotInUse(entity, true);

        entity.setEmployeeNumber(loadEmployeeNumber(entity.getEmailAddress()));
        return super.create(entity);
    }

    @Override
    public UserAccount update(UserAccount entity) {
        validateEmailNotInUse(entity, false);

        entity.setEmployeeNumber(loadEmployeeNumber(entity.getEmailAddress()));
        return super.update(entity);
    }

    private void validateEmailNotInUse(UserAccount entity, boolean createMode) {
        String emailAddress = entity.getEmailAddress();
        UserAccount userAccountByEmail = findByEmailAddress(emailAddress);

        if ((createMode && userAccountByEmail != null) ||
                (!createMode && userAccountByEmail != null && !userAccountByEmail.getId().equals(entity.getId()))) {
            throw new BusinessException(messageUtils.getMessage("err.user.email.not.available", emailAddress));
        }
    }

    private String loadEmployeeNumber(String emailAddress) {
        String employeeNumber = findEmployeeNumber(emailAddress);
        if (employeeNumber == null) {
            throw new BusinessException(messageUtils.getMessage("err.user.email.not.found", emailAddress));
        }
        return employeeNumber;
    }

    private class EmployeeNumberAttributesMapper implements AttributesMapper<String> {
        @Override
        public String mapFromAttributes(Attributes attrs) throws NamingException {
            return (String) attrs.get("employeeNumber").get();
        }
    }
}
