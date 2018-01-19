package demo.app.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class GreetingController {

    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/greeting")
    public String greeting(@RequestParam(value = "name", required = false, defaultValue = "World") String name, Model model) {
        model.addAttribute("test", messageSource.getMessage("app.name", null, null));
        model.addAttribute("name", name);
        return "greeting";
    }

    @RequestMapping(value = "/message", method = RequestMethod.GET)
    public ModelAndView message(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        ModelAndView mav = new ModelAndView("greeting");
        mav.addObject("name", name);
        mav.setViewName("message");
        return mav;
    }

    @ModelAttribute("name")
    @RequestMapping("/sayhi")
    public String sayhi(@RequestParam(value = "name", required = false, defaultValue = "World") String name) {
        return name;
    }
}
