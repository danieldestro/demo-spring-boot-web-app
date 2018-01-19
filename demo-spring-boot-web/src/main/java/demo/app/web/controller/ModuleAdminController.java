package demo.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/admin")
public class ModuleAdminController {

    @RequestMapping
    public String manager() {
        return "admin/home";
    }
}
