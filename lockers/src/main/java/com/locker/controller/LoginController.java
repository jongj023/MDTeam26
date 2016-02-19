package com.locker.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by randyr on 2/12/16.
 */

@Controller
public class LoginController {

    private static final Logger logger =
            LoggerFactory.getLogger(LoginController.class);

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login() {
<<<<<<< HEAD
        logger.info("Login Controller is actually used correctly.");

        return new ModelAndView("login");
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginPost() {
        logger.info("Login posted.");

        return new ModelAndView("index");
    }
=======

        logger.debug("welcome() is executed, value {}", "mkyong");

        ModelAndView model = new ModelAndView("login");

        return model;
        // localhost:8080/WEB-INF/pages/login.jsp
    }

>>>>>>> 135c7393a4e2da329e4f46c7a66a2a43e2aac6f3
}
