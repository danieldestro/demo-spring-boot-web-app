package demo.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/manager")
public class ModuleManagerController {

    @RequestMapping
    public String home() {
        return "manager/home";
    }

    @RequestMapping(value = { "/application", "/application/" })
    public ModelAndView application() {
        return new ModelAndView("manager/apps/apps");
    }
}
