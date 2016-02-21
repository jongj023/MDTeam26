package com.locker.errorhandling;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.google.common.base.Throwables;

/**
 * Created by randyr on 2/21/16.
 */

/*@ControllerAdvice
public class ExceptionHandler {

        /**
         * Handle exceptions thrown by handlers.
         */
        /*@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)
        public ModelAndView exception(Exception exception, WebRequest request) {
            ModelAndView modelAndView = new ModelAndView("error");
            modelAndView.addObject("errorMessage", Throwables.getRootCause(exception));
            return modelAndView;
        }

    }*/
