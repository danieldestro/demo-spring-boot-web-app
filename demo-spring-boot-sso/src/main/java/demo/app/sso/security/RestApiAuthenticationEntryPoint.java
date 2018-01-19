package demo.app.sso.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class RestApiAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger LOG                         = LoggerFactory.getLogger(RestApiAuthenticationEntryPoint.class);

    public static final String  DEFAULT_REST_API_ENTRYPOINT = "/api/**";

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
            throws IOException, ServletException {

        LOG.debug("RestUnauthorizedEntryPoint - Authentication failed");
        LOG.debug("  => URL: " + request.getRequestURI());

        response.setHeader("WWW-Authenticate", "FormBased");
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}
