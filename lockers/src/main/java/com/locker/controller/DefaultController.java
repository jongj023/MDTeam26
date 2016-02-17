package com.locker.controller;

/**
 * Created by randyr on 2/12/16.
 */

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class DefaultController {

    @RequestMapping("/")
    public ModelAndView index() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("msg", "I actually work after 50 times.");
        return model;
    }

    @RequestMapping("/test")
    public String test() {
        return "testresult";
    }
}
