package com.locker.controller;

import com.locker.model.UserEntity;
import com.locker.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by randyr on 2/12/16.
 *
 * This controller handles all the login, logout and register requests. Makes use of spring security.
 */

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    private static final Logger logger =
            LoggerFactory.getLogger(LoginController.class);


    @RequestMapping(value = "/login", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ModelAndView login(
            @RequestParam(value = "error", required = false) String error,
            @RequestParam(value = "logout", required = false) String logout,
            @RequestParam(value = "registered", required = false) String registered) {

        ModelAndView model = new ModelAndView();
        if (error != null) {
            model.addObject("error", error);
        } if (logout != null) {
            model.addObject("msg", logout);
        } if (registered != null) {
            model.addObject("registered", true);
        }
        model.setViewName("login");

        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public ModelAndView register(
            @RequestParam(value = "error", required = false) boolean error) {
        ModelAndView model = new ModelAndView("register");
        if (error) {
            logger.error("Registration attempt with already taken username");
            model.addObject("error", true);
        }
        return model;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @PreAuthorize("permitAll")
    public String register(@ModelAttribute @Valid UserEntity user) {
        boolean result = userService.registerNewUser(user);
        return result ? "redirect:/login?registered" : "redirect:/register?error";
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    @PreAuthorize("permitAll")
    public String logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.isAuthenticated()){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout";
    }

}
