package com.locker.controller;

import com.google.common.base.Throwables;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by rtheuns on 6/12/16.
 */

@Controller
public class ErrorController {

    @RequestMapping(value = {"/error", "error", "404"}, method = RequestMethod.GET)
    public ModelAndView error(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView error = new ModelAndView();
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
        String errorMessage = getErrorMessage(throwable, statusCode);
        String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
        if (requestUri == null) {
            requestUri = "Unknown";
        }

        error.addObject("errorTitle", statusCode + " - " + errorMessage);
        error.addObject("errorUri", requestUri);

        System.out.println("Error page called\t" + statusCode + "\t" + errorMessage + "\t" + requestUri);
        return error;
    }

    private String getErrorMessage(Throwable throwable, Integer statusCode) {
        if (throwable != null) {
            return Throwables.getRootCause(throwable).getMessage();
        }
        HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
        return httpStatus.getReasonPhrase();
    }
}
