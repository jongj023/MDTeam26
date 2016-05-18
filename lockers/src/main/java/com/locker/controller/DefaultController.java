package com.locker.controller;

/**
 * Created by randyr on 2/12/16.
 */

import com.locker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class DefaultController {

    @Autowired
    private LockerService lockerService;

    @RequestMapping("/")
    public ModelAndView index() {
        RedirectView view = new RedirectView("/locker", true);
        view.setExposeModelAttributes(false);
        return new ModelAndView(view);
    }

    @RequestMapping("/test")
    public String testController(Model model) {
        System.out.println("ALL LOCKERS:    " + lockerService.findAll());
        return "index"; //Can be used to test things such as spring security
    }

    @RequestMapping("/help")
    public String help(Model model){
        return "help";
    }
}
