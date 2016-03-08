package com.locker.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by randyr on 2/20/16.
 */

@Controller
public class LockerController {

    @RequestMapping(value = "/locker", method = RequestMethod.GET)
    public ModelAndView mainLockerApplication() {
        ModelAndView model = new ModelAndView("index");
        model.addObject("locker", true);
        return model;
    }
}
