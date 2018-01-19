package demo.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/security")
public class ModuleSecurityController {

    @RequestMapping
    public String home() {
        return "security/home";
    }

    @RequestMapping(value = { "/roles", "/roles/" })
    public ModelAndView userRole() {
        return new ModelAndView("security/roles/roles");
    }

    @RequestMapping(value = { "/permissions", "/permissions/" })
    public ModelAndView permission() {
        return new ModelAndView("security/permissions/permissions");
    }

    @RequestMapping(value = { "/users", "/users/" })
    public ModelAndView user() {
        return new ModelAndView("security/users/users");
    }
}
