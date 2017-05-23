/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misho
 */
@Controller
public class userBudgetController {

    @RequestMapping(value = "/userBudget", method = RequestMethod.GET)
    public ModelAndView index(@RequestParam("year") int year, @RequestParam("month") int month,
            ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<PaymentType> paymentTypes = context
                .getPaymentTypeSet()
                .GetAll();
        List<PaymentCategory> categories = context
                .getPaymentCategorySet()
                .GetAllActiveCategoriesForUser(user.getId());
        DateFormat format = new SimpleDateFormat("MM/yyyy");
        boolean exists = true;
        try {
            context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), format.parse(month + "/" + year));
        } catch (ParseException ex) {
            System.out.println("could not parse date.");
        } catch (EntityNotFoundException exp) {
            exists = false;
        }
        ModelAndView view = new ModelAndView("user-budget");
        view.addObject("categories",categories);
        view.addObject("month",month);
        view.addObject("year",year);
        view.addObject("exists",exists);
        view.addObject("paymentTypes", paymentTypes);
        return view;
    }
}
