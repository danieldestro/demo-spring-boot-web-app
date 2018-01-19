package demo.app.web.security;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

/**
 * CsrfHeaderFilter extracts the CSRF token from the request attribute (provided by Spring Security) and sets it as a
 * cookie. This XSRF-TOKEN cookie is then used by web UI javascript frameworks to set a CSRF token in the request
 * header.
 */
public class CsrfHeaderFilter extends OncePerRequestFilter {

    private static final Logger LOG  = LoggerFactory.getLogger(CsrfHeaderFilter.class);

    private boolean             secure;
    private String              path = "/";

    public CsrfHeaderFilter() {
        this(false);
    }

    public CsrfHeaderFilter(boolean secure) {
        this(secure, "/");
    }

    public CsrfHeaderFilter(boolean secure, String path) {
        this.secure = secure;
        this.path = path;
        LOG.debug("CsrfHeaderFilter created - CSRF Cookie is {} secure and path is {}", (secure ? "" : "not"), path);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
        if (csrf != null) {
            Cookie cookie = WebUtils.getCookie(request, SecurityUtils.CSRF_TOKEN_COOKIE_NAME);
            String token = csrf.getToken();
            if (cookie == null || Objects.equals(token, cookie.getValue())) {
                cookie = new Cookie(SecurityUtils.CSRF_TOKEN_COOKIE_NAME, token);
                cookie.setPath(path);

                // CSRF cookie cannot be set as HttpOnly, otherwise javascript will not work
                cookie.setHttpOnly(false);
                cookie.setSecure(secure);

                response.addCookie(cookie);
            }
        }
        filterChain.doFilter(request, response);
    }

    public boolean isSecure() {
        return secure;
    }

    public String getPath() {
        return path;
    }
}
