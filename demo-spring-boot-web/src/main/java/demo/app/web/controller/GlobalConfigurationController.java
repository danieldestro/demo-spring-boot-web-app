package demo.app.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import demo.app.manager.service.GlobalConfigurationService;

@Controller
@RequestMapping(value = "/admin/configuration")
public class GlobalConfigurationController {

    @Autowired
    private GlobalConfigurationService service;

    @RequestMapping(value = { "", "/" })
    public ModelAndView home() {

        List<String> contexts = service.listContexts();

        ModelAndView mav = new ModelAndView("admin/configs/configs");
        mav.addObject("contexts", contexts);
        return mav;
    }
}