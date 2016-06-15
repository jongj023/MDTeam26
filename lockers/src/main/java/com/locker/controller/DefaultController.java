package com.locker.controller;

/**
 * Created by randyr on 2/12/16.
 */

import com.locker.service.LockerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("isAuthenticated()")
    public ModelAndView index() {
        RedirectView view = new RedirectView("/locker", true);
        view.setExposeModelAttributes(false);
        return new ModelAndView(view);
    }

    @RequestMapping("/test")
    @Secured("ADMIN")
    public String testController(Model model) {
        return "locker";
    }
}
