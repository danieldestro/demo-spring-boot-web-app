package demo.app.core.domain;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * This class implements a Spring JPA entity auditor aware interface.
 */
public class EntityAuditorAwareImpl implements AuditorAware<String> {

    /**
     * Returns the authenticated user employee e-mail address.
     * 
     * @return returns user employee number or null if not authenticated.
     */
    @Override
    public String getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            return null;
        }

        return ((User) authentication.getPrincipal()).getEmailAddress();
    }
}
