package demo.app.web.sso.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/security", produces = MediaType.APPLICATION_JSON_VALUE)
public class AuthRestController {

    @GetMapping("/login")
    @PostMapping("/login")
    public String login() {
        return "success";
    }
}
