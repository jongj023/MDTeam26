package com.locker.controller;

/**
 * Created by randyr on 2/12/16.
 */

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        model.addObject("loggedIn", auth.isAuthenticated());
        return model;
    }

    @RequestMapping("/test")
    public String testController() {
        //Can be used to test things such as spring security
        return "Test result";
    }
}
