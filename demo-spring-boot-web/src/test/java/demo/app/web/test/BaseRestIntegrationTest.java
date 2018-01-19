package demo.app.web.test;

import static demo.app.web.test.CustomSecurityMockMvcRequestPostProcessors.httpBasicUserAdmin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.testng.Assert.assertTrue;

import java.util.Collection;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.DefaultCsrfToken;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.util.Base64Utils;

/**
 * Base Spring Boot Integration Test class for Rest Services. All Integration Test classes for Rest Services must extends this class. <br>
 * This class can be configured to work as a TestNG test suite runtime or a JUnit flavor. <br>
 * Don't forget to use the correspondent @Test annotation according to the test engine chosen.
 */
public abstract class BaseRestIntegrationTest extends BaseWebIntegrationTest {

    private static final Logger LOG           = LoggerFactory.getLogger(BaseRestIntegrationTest.class);

    private static final String URI_API_LOGIN = "/api/login";

    @Autowired
    private TestRestTemplate    restTemplate;

    @Autowired
    private CsrfTokenRepository csrfTokenRepository;

    protected TestRestTemplate getRestTemplate() {
        return restTemplate;
    }

    protected TestRestTemplate getRestTemplateAuth() {
        return getRestTemplateAuth(LOGIN_USER, LOGIN_PASSWORD);
    }

    protected TestRestTemplate getRestTemplateAuth(String usr, String pwd) {
        return restTemplate.withBasicAuth(usr, pwd);
    }

    protected String performApiGet(String uri, ResultMatcher... status) throws Exception {
        return performApiGet(uri, null, status);
    }

    protected String performApiGet(String uri, String usr, String pwd, ResultMatcher... status) throws Exception {
        return performApiGet(uri, httpBasic(usr, pwd), status);
    }

    protected String performApiGet(String uri, RequestPostProcessor postProcessor, ResultMatcher... status) throws Exception {

        ResultActions result = performGet(uri, postProcessor, status)
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE));

        MvcResult response = result.andReturn();

        String json = response.getResponse().getContentAsString();

        assertJsonResponse(json);

        return json;
    }

    protected void assertJsonResponse(String json) {
        assertTrue(json.startsWith("{"));
        assertTrue(json.endsWith("}"));
    }

    protected <T> HttpEntity<T> buildHttpEntity(T body, boolean csrf, boolean readCsrfCookie) throws Exception {
        HttpHeaders headers = csrf ? csrfHeaders(readCsrfCookie, null) : basicAuthHeaders();
        HttpEntity<T> httpEntity = new HttpEntity<>(body, headers);
        return httpEntity;
    }

    protected HttpHeaders basicAuthHeaders() {
        String credentials = LOGIN_USER + ":" + LOGIN_PASSWORD;
        byte[] base64CredsBytes = Base64Utils.encode(credentials.getBytes());
        String base64Creds = new String(base64CredsBytes);

        HttpHeaders headers = new HttpHeaders();
        String authHeader = "Basic " + new String(base64Creds);
        // headers.set("Authorization", authHeader);
        LOG.debug("Authorization: {}", authHeader);

        return headers;
    }

    protected HttpHeaders csrfHeaders(boolean readCookie, HttpServletRequest request) throws Exception {
        // MvcResult res = performLogin().andReturn();
        // printHeadersAndCookies(res);
        // res = performGet(URI).andReturn();
        // printHeadersAndCookies(res);
        // res = performGet("/manager/workspace").andReturn();
        // printHeadersAndCookies(res);

        // MvcResult result = performGet("/api/login", authenticated()).andReturn();
        // printHeadersAndCookies(result);

        CsrfToken csrfToken = null;
        if (readCookie) {
            MvcResult result = performGet(URI_API_LOGIN + "30", httpBasicUserAdmin()).andReturn();
            printHeadersAndCookies(result);
            Cookie cookie = result.getResponse().getCookie("XSRF-TOKEN");
            csrfToken = new DefaultCsrfToken("XSRF-TOKEN", "XSRF-TOKEN", cookie != null ? cookie.getValue() : "n/a");
        } else {
            csrfToken = request != null ? csrfTokenRepository.loadToken(request) : csrfTokenRepository.generateToken(null);
        }

        // CsrfToken csrfToken = csrfTokenRepository.generateToken(null);

        HttpHeaders headers = basicAuthHeaders();
        headers.set(csrfToken.getHeaderName(), csrfToken.getToken());
        headers.set("Cookie", "XSRF-TOKEN=" + csrfToken.getToken());

        LOG.debug("CSRF Param:  {}", csrfToken.getParameterName());
        LOG.debug("CSRF Header: {}", csrfToken.getHeaderName());
        LOG.debug("CSRF Token:  {}", csrfToken.getToken());

        return headers;
    }

    protected void printHeadersAndCookies(MvcResult response) {
        LOG.info("===== RESPONSE HEADERS =====");
        Collection<String> headers = response.getResponse().getHeaderNames();
        LOG.info("headers {}", headers.size());
        for (String header : headers) {
            LOG.info("header - {}: {}", header, response.getResponse().getHeaderValue(header));
        }
        LOG.info("===== RESPONSE COOKIES =====");
        Cookie[] cookies = response.getResponse().getCookies();
        LOG.info("cookies {}", cookies.length);
        for (Cookie cookie : cookies) {
            LOG.info("cookie - {}: {}", cookie.getName(), cookie.getValue());
        }
    }
}
