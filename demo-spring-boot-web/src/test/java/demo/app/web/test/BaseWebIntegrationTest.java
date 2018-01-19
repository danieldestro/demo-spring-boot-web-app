package demo.app.web.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.logout;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;

/**
 * Base Spring Boot Integration Test class. All Integration Test classes must extends this class. <br>
 * This class can be configured to work as a TestNG test suite runtime or a JUnit flavor. <br>
 * Don't forget to use the correspondent @Test annotation according to the test engine chosen.
 */
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public abstract class BaseWebIntegrationTest extends TestNGWebIntegrationTest {
    // if you are going the JUnit way
    // extends WebJUnitTestConfig {

    protected static final String LOGIN_USER           = "5";
    protected static final String LOGIN_PASSWORD       = "";

    protected static final String LOGIN_USER_LOCKED    = "1";
    protected static final String LOGIN_USER_NOT_FOUND = "9999999";

    protected static final String LOGIN_REST_API       = "/api/security/login";
    protected static final String LOGIN_FORM_URI       = "/login";

    private MockMvc               mvc;

    @Autowired
    private WebApplicationContext webAppContext;

    @BeforeClass
    public void setup() throws Exception {
        mvc = webAppContextSetup(webAppContext).apply(springSecurity()).build();
    }

    protected MockMvc getMockMvc() {
        return mvc;
    }

    protected ResultActions performLogin() throws Exception {
        return performLogin(LOGIN_USER, LOGIN_PASSWORD, status().is3xxRedirection(), authenticated());
    }

    protected ResultActions performLogin(String usr, String pwd) throws Exception {
        return performLogin(LOGIN_USER, LOGIN_PASSWORD, status().is3xxRedirection(), authenticated());
    }

    protected ResultActions performLogin(String usr, String pwd, ResultMatcher... status) throws Exception {
        ResultActions result = getMockMvc().perform(formLogin().user(usr).password(pwd));
        if (status != null) {
            for (ResultMatcher st : status) {
                result.andExpect(st);
            }
        }
        return result;
    }

    protected ResultActions performBasicLogin() throws Exception {
        return performBasicLogin(LOGIN_USER, LOGIN_PASSWORD, status().isOk(), authenticated());
    }

    protected ResultActions performBasicLogin(String usr, String pwd) throws Exception {
        return performBasicLogin(usr, pwd, status().isOk(), authenticated());
    }

    protected ResultActions performBasicLogin(String usr, String pwd, ResultMatcher... status) throws Exception {
        RequestBuilder requestBuilder = get(LOGIN_REST_API).with(httpBasic(usr, pwd));
        ResultActions result = getMockMvc().perform(requestBuilder);
        if (status != null) {
            for (ResultMatcher st : status) {
                result.andExpect(st);
            }
        }
        return result;
    }

    protected ResultActions performLogout() throws Exception {
        return mvc.perform(logout()).andExpect(unauthenticated());
    }

    protected ResultActions performGet(String uri, ResultMatcher... status) throws Exception {
        return performRequest(get(uri), status);
    }

    protected ResultActions performGet(String uri, String usr, String pwd, ResultMatcher... status) throws Exception {
        return performGet(uri, httpBasic(usr, pwd), status);
    }

    protected ResultActions performGet(String uri, RequestPostProcessor postProcessor, ResultMatcher... status) throws Exception {
        return performGet(uri, postProcessor, true, status);
    }

    protected ResultActions performGet(String uri, RequestPostProcessor postProcessor, boolean csrf, ResultMatcher... status) throws Exception {
        MockHttpServletRequestBuilder request = csrf ? get(uri).with(csrf()) : get(uri);
        if (postProcessor != null) {
            request.with(postProcessor);
        }
        return performRequest(request, status);
    }

    protected ResultActions performRequest(RequestBuilder request, ResultMatcher... status) throws Exception {
        ResultActions result = getMockMvc().perform(request);
        if (status != null) {
            for (ResultMatcher st : status) {
                result.andExpect(st);
            }
        }
        return result;
    }
}
