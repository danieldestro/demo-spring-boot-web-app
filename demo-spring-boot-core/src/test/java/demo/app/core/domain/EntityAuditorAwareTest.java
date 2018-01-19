package demo.app.core.domain;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EntityAuditorAwareTest {

    private static final String  EMP_EMAIL = "user@demo.com";

    @Mock
    private Authentication       authentication;

    @Mock
    private User                 user;

    @InjectMocks
    private AuditorAware<String> auditor   = new EntityAuditorAwareImpl();

    @BeforeMethod
    public void beforeMethod() {
        MockitoAnnotations.initMocks(this);

        when(authentication.getPrincipal()).thenReturn(user);
        when(user.getEmailAddress()).thenReturn(EMP_EMAIL);

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @AfterMethod
    public void afterMethod() {
        SecurityContextHolder.getContext().setAuthentication(null);
    }

    @Test
    public void getCurrentAuditor_not_auth() {
        assertNull(auditor.getCurrentAuditor());
    }

    @Test
    public void getCurrentAuditor_not_auth_2() {
        when(authentication.isAuthenticated()).thenReturn(false);

        assertNull(auditor.getCurrentAuditor());
    }

    @Test
    public void getCurrentAuditor_not_auth_3() {
        SecurityContextHolder.getContext().setAuthentication(null);

        assertNull(auditor.getCurrentAuditor());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void getCurrentAuditor_no_principal_npe() {
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(null);

        auditor.getCurrentAuditor();
    }

    @Test
    public void getCurrentAuditor() {
        when(authentication.isAuthenticated()).thenReturn(true);

        assertNotNull(auditor.getCurrentAuditor());
        assertEquals(auditor.getCurrentAuditor(), EMP_EMAIL);
    }
}
