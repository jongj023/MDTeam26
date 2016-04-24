package com.locker.controller;

import com.locker.model.LockerEntity;
import com.locker.service.LockerService;
import com.locker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

/**
 * Created by randyr on 2/20/16.
 */

@Controller
public class LockerController {

    @Autowired
    LockerService lockerService;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/locker", method = RequestMethod.GET)
    public ModelAndView mainLockerApplication() {
        return getDefaultLocker();
    }

    @RequestMapping(value = "/locker/{id}",method = RequestMethod.GET)
    public ModelAndView lockerWithId(@PathVariable Long id) {
        ModelAndView model = new ModelAndView("");
        LockerEntity locker = lockerService.findLockerById(id);
        return model;
    }

    @RequestMapping(value = "/setuser", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView updateLockerWithUser(@ModelAttribute("locker-id") Long id, @ModelAttribute("locker-user") String user) {
        //TODO voorkomen dat user welke al kluisje heeft nog een tweede kluisje kan krijgen.
        lockerService.setUser(id, user);
        RedirectView view = new RedirectView("/locker", true);
        view.setExposeModelAttributes(false);
        return new ModelAndView(view);
    }

    private ModelAndView getDefaultLocker() {
        ModelAndView model = new ModelAndView("locker");
        model.addObject("lockers", lockerService.findAll());
        return model;
    }
}
