package demo.app.web.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    public static final String        HEADER_NAME_X_REQUESTED_WITH  = "X-Requested-With";
    public static final String        HEADER_VALUE_X_REQUESTED_WITH = "XMLHttpRequest";

    public static final String        CSRF_TOKEN_COOKIE_NAME        = "XSRF-TOKEN";
    public static final String        CSRF_TOKEN_HEADER_NAME        = "X-CSRF-TOKEN";

    private static final ObjectMapper mapper                        = new ObjectMapper();

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     */
    public static String getCurrentLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        UserDetails springSecurityUser = null;
        String userName = null;

        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                springSecurityUser = (UserDetails) authentication.getPrincipal();
                userName = springSecurityUser.getUsername();
            } else if (authentication.getPrincipal() instanceof String) {
                userName = (String) authentication.getPrincipal();
            }
        }

        return userName;
    }

    public static void sendResponse(HttpServletResponse response, int status, Object object) throws IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(object));
        response.setStatus(status);
        writer.flush();
        writer.close();
    }

    public static void sendError(HttpServletResponse response, Exception exception, int status, String message) throws IOException {
        Map<String, String> error = new HashMap<>();
        error.put("status", String.valueOf(status));
        error.put("message", message);
        error.put("error", exception.getMessage());

        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(status);

        PrintWriter writer = response.getWriter();
        writer.write(mapper.writeValueAsString(error));
        writer.flush();
        writer.close();
    }

    public static boolean isAjaxRequest(HttpServletRequest request) {
        String xrequestedWith = request.getHeader(HEADER_NAME_X_REQUESTED_WITH);
        return HEADER_VALUE_X_REQUESTED_WITH.equalsIgnoreCase(xrequestedWith);
    }
}