package demo.app.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "/demo")
public class DemoModuleController {

    @RequestMapping
    public String home() {
        return "demo/home";
    }

    @RequestMapping(value = { "/security", "/security/" })
    public ModelAndView demo() {
        return new ModelAndView("demo/security/security");
    }

}