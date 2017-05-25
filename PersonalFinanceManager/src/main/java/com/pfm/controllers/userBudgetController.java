/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.CategoryBudgetData;
import com.pfm.data.data.UserBudgetData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.BasicException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misho
 */
@Controller
public class userBudgetController {

    @RequestMapping(value = "/userBudget", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
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
        ModelAndView view = new ModelAndView("user-budget");
        view.addObject("categories", categories);
        view.addObject("paymentTypes", paymentTypes);
        return view;
    }

    @RequestMapping(value = "/userBudget", method = RequestMethod.POST)
    public ModelAndView saveBudget(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
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
        String budgetDate = request.getParameter("budgetDate");
        String[] budgetMonthAndYear = budgetDate.split("/");
        int year = Integer.parseInt(budgetMonthAndYear[0]);
        int month = Integer.parseInt(budgetMonthAndYear[1]) - 1;
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
        Date start = calendar.getTime();
        int lastDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(year, month, lastDay);
        Date end = calendar.getTime();
        UserBudgetData budgetData = new UserBudgetData();
        budgetData.setFromDate(start);
        budgetData.setToDate(end);
        budgetData.setUser(user.getId());
        UUID budgetId = null;
        try {
            budgetId = context.getUserBudgetSet().AddOrUpdate(budgetData);
        } catch (BasicException ex) {
            System.out.println(ex.getMessage());
        }
        for (PaymentCategory category : categories) {
            String amountForCategory = request.getParameter("category_" + category.getId().toString());
            Double amount = Double.parseDouble(amountForCategory);
            CategoryBudgetData budgetCategory = new CategoryBudgetData();
            budgetCategory.setActive(true);
            budgetCategory.setAmount(amount);
            budgetCategory.setBudgetId(budgetId);
            budgetCategory.setCategoryId(category.getId());
            try {
                context.getCategoryDetailSet().AddOrUpdate(budgetCategory);
            } catch (BasicException ex) {
                System.out.println(ex.getMessage());
            }
        }
        ModelAndView view = new ModelAndView("user-budget");
        view.addObject(
                "categories", categories);
        view.addObject(
                "paymentTypes", paymentTypes);
        return view;
    }
    
    @RequestMapping(value = "/userBudgetStatus", method = RequestMethod.GET)
    public ModelAndView budgetStatus(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        
        ModelAndView view = new ModelAndView("user-budget-status");
        return view;
    }
}
