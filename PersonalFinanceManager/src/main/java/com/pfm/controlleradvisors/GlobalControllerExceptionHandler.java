/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controlleradvisors;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryEditException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeAddException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeEditException;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.exceptions.PageNotFoundException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.util.List;
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
    @ExceptionHandler(
            {
                NoHandlerFoundException.class,
                PageNotFoundException.class
            }
    )
    public String handleNotFound(Exception ex) {
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
        IpfmContext context = pfmContext.getInstance();
        List<PaymentType> types = context
                .getPaymentTypeSet().GetAll();
        mav.addObject("errorMessage", "Вече съществува категория с това име.");
        mav.addObject("types", types);
        mav.setViewName("categories-add");
        return mav;
    }

    @ExceptionHandler(PaymentCategoryEditException.class)
    public ModelAndView PaymentCategoryEditException(PaymentCategoryEditException ex) {
        ModelAndView mav = new ModelAndView();
        IpfmContext context = pfmContext.getInstance();
        List<PaymentType> types = context
                .getPaymentTypeSet().GetAll();
        //PaymentCategory category = context.getPaymentCategorySet().GetById(categoryId);
        //mav.addObject("category", category);
        mav.addObject("types", types);
        mav.addObject("errorMessage", "Вече съществува категория с това име.");
        mav.setViewName("categories-add");
        return mav;
    }

    @ExceptionHandler(PaymentTypeAddException.class)
    public ModelAndView handlePaymentTypeAdd(PaymentTypeAddException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Вече съществува тип на плащания с това име.");
        mav.setViewName("types-add");
        return mav;
    }

    @ExceptionHandler(PaymentTypeEditException.class)
    public ModelAndView PaymentTypeEditException(PaymentTypeEditException ex) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("errorMessage", "Вече съществува тип на плащания с това име.");
        mav.setViewName("types-add");
        return mav;
    }
}
