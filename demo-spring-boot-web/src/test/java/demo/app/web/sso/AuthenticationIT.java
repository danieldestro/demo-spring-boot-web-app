package demo.app.web.sso;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.testng.annotations.Test;
import org.testng.collections.Lists;

import demo.app.web.config.AppConfigProperties;
import demo.app.web.test.BaseRestIntegrationTest;

public class AuthenticationIT extends BaseRestIntegrationTest {

    protected static final String DEMO_REST_API       = "/api/demo/security";

    protected static final String LOGIN_USER_NO_DEMO  = "206";
    protected static final String LOGIN_USER_HAS_DEMO = "6";

    @Autowired
    protected AppConfigProperties config;

    @Test
    public void formAuth_custom() throws Exception {
        getMockMvc().perform(formLogin().user(LOGIN_USER).password(LOGIN_PASSWORD)).andExpect(authenticated().withUsername(LOGIN_USER));
    }

    // TODO: this test will work when we fix the problem with Roles and Authorities and Permissions
    @Test(enabled = false)
    public void formAuth_roles() throws Exception {
        performLogin().andExpect(authenticated().withUsername(LOGIN_USER).withRoles("ADMIN"));
    }

    @Test
    public void formAuth_authorities() throws Exception {
        List<GrantedAuthority> authorities = Lists.newArrayList("ROLE_ADMIN", "CAC_VIEW_DEMO_PAGE", "CAC_EDIT_DEMO_PAGE").stream()
                .map(permission -> new SimpleGrantedAuthority(permission)).collect(Collectors.toList());

        performLogin().andExpect(authenticated().withUsername(LOGIN_USER).withAuthorities(authorities));
    }

    @Test
    public void formAuth() throws Exception {
        performLogin();
    }

    @Test
    public void formAuth_user() throws Exception {
        performLogin(LOGIN_USER, LOGIN_PASSWORD);
    }

    @Test
    public void formAuth_user_status() throws Exception {
        performLogin(LOGIN_USER, LOGIN_PASSWORD, status().is3xxRedirection(), authenticated());
    }

    @Test
    public void formAuth_user_locked() throws Exception {
        performLogin(LOGIN_USER_LOCKED, LOGIN_PASSWORD, status().is3xxRedirection(), unauthenticated());
    }

    @Test
    public void formAuth_user_not_found() throws Exception {
        performLogin(LOGIN_USER_NOT_FOUND, LOGIN_PASSWORD, status().is3xxRedirection(), unauthenticated());
    }

    @Test
    public void basicAuth() throws Exception {
        performBasicLogin();
    }

    @Test
    public void basicAuth_user() throws Exception {
        performBasicLogin(LOGIN_USER, LOGIN_PASSWORD);
    }

    @Test
    public void basicAuth_user_status() throws Exception {
        performBasicLogin(LOGIN_USER, LOGIN_PASSWORD, status().isOk(), authenticated());
    }

    @Test
    public void basicAuth_user_locked() throws Exception {
        performBasicLogin(LOGIN_USER_LOCKED, LOGIN_PASSWORD, status().isUnauthorized(), unauthenticated());
    }

    @Test
    public void basicAuth_user_not_found() throws Exception {
        performBasicLogin(LOGIN_USER_NOT_FOUND, LOGIN_PASSWORD, status().isUnauthorized(), unauthenticated());
    }

    @Test
    public void logout() throws Exception {
        performLogout().andExpect(unauthenticated());
    }

    @Test
    public void api_call_not_authenticated() throws Exception {
        performGet(DEMO_REST_API, unauthenticated(), status().isUnauthorized());
    }

    @Test
    public void api_call_forbidden() throws Exception {
        performGet(DEMO_REST_API, LOGIN_USER_NO_DEMO, LOGIN_PASSWORD, authenticated(), status().isForbidden());
    }

    @Test
    public void api_call_authorized() throws Exception {
        performGet(DEMO_REST_API, LOGIN_USER_HAS_DEMO, LOGIN_PASSWORD, authenticated(), status().isOk());
    }

    @Test
    public void api_call_with_csrf_valid() throws Exception {
        RequestBuilder request = buildDemoApiRequest(true);
        performRequest(request, authenticated(), status().isOk());
    }

    @Test
    public void api_call_without_csrf_invalid() throws Exception {
        RequestBuilder request = buildDemoApiRequest(false);

        if (config.getSecurity().getCsrf().isDisabled()) {
            // this test only works if CSRF is disabled
            performRequest(request, authenticated(), status().isOk());
        } else {
            // this test only works if CSRF is enabled
            performRequest(request, unauthenticated(), status().isForbidden());
        }
    }

    private RequestBuilder buildDemoApiRequest(boolean csrf) {
        MockHttpServletRequestBuilder request = post(DEMO_REST_API).with(httpBasic(LOGIN_USER_HAS_DEMO, LOGIN_PASSWORD));
        request.contentType("application/json");
        request.content("{\"name\":\"Demo AuthenticationTest\"}");
        return csrf ? request.with(csrf()) : request;
    }
}
