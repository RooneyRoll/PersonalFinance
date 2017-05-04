/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controlleradvisors;

import com.pfm.data.exceptions.PaymentCategoryAddException;
import com.pfm.data.exceptions.UserRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author Misho
 */

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex) {
        return "404";
    }
    
    @ExceptionHandler(UserRegisterException.class)
    public ModelAndView handleRegister(UserRegisterException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Потребителското име вече е заето.");
        mav.setViewName("register");
        return mav;
    }
    
    @ExceptionHandler(PaymentCategoryAddException.class)
    public ModelAndView handlePaymentCategoryAdd(PaymentCategoryAddException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Вече съществува категория с това име.");
        mav.setViewName("categories-add");
        return mav;
    }
}
