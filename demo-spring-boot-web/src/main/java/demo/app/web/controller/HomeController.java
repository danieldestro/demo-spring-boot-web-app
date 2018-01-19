package demo.app.web.controller;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @RequestMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @RequestMapping("/workspace")
    public String workspace() {
        return "workspace";
    }

    @RequestMapping("/testing")
    public String test(Model model) {
        model.addAttribute("time", new Date());
        model.addAttribute("message", "TEST MSG");
        return "test";
    }
}