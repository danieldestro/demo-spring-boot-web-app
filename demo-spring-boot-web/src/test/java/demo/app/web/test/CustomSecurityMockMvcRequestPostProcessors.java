package demo.app.web.test;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

import org.springframework.test.web.servlet.request.RequestPostProcessor;

public class CustomSecurityMockMvcRequestPostProcessors {

    /**
     * Use this method to provide mock credentials.
     * 
     * @return
     */
    public static RequestPostProcessor mockUserAdmin() {
        return user("admin").password("password").roles("ADMIN");
    }

    /**
     * Use this method to provide real credentials.
     * 
     * @return
     */
    public static RequestPostProcessor httpBasicUserAdmin() {
        return httpBasic("5", "");
    }
}
