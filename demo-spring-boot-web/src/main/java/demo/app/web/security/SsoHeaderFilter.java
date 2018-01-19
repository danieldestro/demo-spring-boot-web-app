package demo.app.web.security;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.preauth.RequestHeaderAuthenticationFilter;

/**
 * Get the Employee Number from the UUID header.
 */
public class SsoHeaderFilter extends RequestHeaderAuthenticationFilter {

    private static final Logger LOG                             = LoggerFactory.getLogger(SsoHeaderFilter.class);

    private boolean             allowPreAuthenticatedPrincipals = true;

    /**
     * Array of URI that should be ignored by the filter. This array should contain the initial part of the URI (after
     * context path). i.e. "/images/", "/css/".
     */
    private List<String>        ignoreUris;

    public SsoHeaderFilter() {
        super();
        setPrincipalRequestHeader("PF_AUTH_EMPLOYEENUMBER");
        LOG.debug("LOADING {}", this);
    }

    public boolean isAllowPreAuthenticatedPrincipals() {
        return allowPreAuthenticatedPrincipals;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if (!ignoreResource(httpRequest)) {
            LOG.trace("{} :: method: {}, uri: {}", getClass().getSimpleName(), httpRequest.getMethod(), httpRequest.getRequestURI());
            super.doFilter(request, response, chain);

        } else {
            LOG.trace("Ignoring resource URI: {}", httpRequest.getRequestURI());
            chain.doFilter(request, response);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authResult)
            throws IOException, ServletException {
        LOG.trace("{} successfulAuthentication", getClass().getSimpleName());
        super.successfulAuthentication(request, response, authResult);

    }

    /**
     * Check whether the resource URI is in the ignore list and should not be evaluated. If the URI starts with the
     * context path + the ignore URI, then the resource should be ignored. i.e. <br>
     * 
     * URI: "/<web-context>/images/logo.png" and Ignore URI: "/images/", then returns true. <br>
     * That means it will ignore all resources under that "images" URI.
     * 
     * @param request
     *            http servlet request
     * @return true if the resource should be ignored, false otherwise.
     */
    protected boolean ignoreResource(HttpServletRequest request) {
        List<String> list = getIgnoreUris();
        if (list == null) {
            return false;
        }
        final String uri = request.getRequestURI();
        final String context = getWebContextname(request);
        Optional<String> optional = list.stream().filter(ignoreUri -> uri.startsWith(context + ignoreUri)).findFirst();
        return optional.isPresent();
    }

    private String getWebContextname(HttpServletRequest request) {
        String context = request.getContextPath();
        if (context.endsWith("/")) {
            context = context.substring(0, context.length() - 1);
        }
        return context;
    }

    public List<String> getIgnoreUris() {
        return ignoreUris;
    }

    public void setIgnoreUris(List<String> ignoreUris) {
        this.ignoreUris = ignoreUris;
    }
}
