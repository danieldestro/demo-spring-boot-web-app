package demo.app.sso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

import demo.app.core.service.CrudService;
import demo.app.sso.domain.UserAccount;

public interface UserAccountService extends CrudService<UserAccount, Integer>, UserDetailsService {

    /**
     * Finds a user account based on the provided e-mail.
     * 
     * @param email
     * @param page
     * @return
     */
    Page<UserAccount> findByEmailAddressContaining(String email, Pageable page);

    /**
     * User login functionality
     */
    UserAccount getLoggedUserAccount();

    /**
     * Checks whether the current user has a specific role or not.
     * 
     * @param roleName
     * @return
     */
    boolean userHasRole(String roleName);

    /**
     * Checks whether the current user has a specific authority or not.
     * 
     * @param authorityName
     * @return
     */
    boolean userHasAuthority(String authorityName);

    /**
     * Returns an user account based on the provided e-mail.
     * 
     * @param email
     * @return
     */
    UserAccount findByEmailAddress(String email);

    /**
     * Loads an employee number from LDAP based on the received e-mail.
     * 
     * @param email
     * @return
     */
    String findEmployeeNumber(String email);
}
