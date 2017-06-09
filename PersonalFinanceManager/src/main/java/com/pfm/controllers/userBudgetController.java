/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.CategoryBudgetData;
import com.pfm.data.data.RecurringBudgetPaymentData;
import com.pfm.data.data.UserBudgetData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.RecurringType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.BasicException;
import com.pfm.exceptions.ValidationException;
import com.pfm.models.payment.PaymentAddModel;
import com.pfm.models.recurringBudgetPayment.RecurringBudgetPaymentAddModel;
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
import org.springframework.web.bind.annotation.ModelAttribute;
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
            double amount = Double.parseDouble(amountForCategory);
            if (amount < 0) {
                amount = 0;
            }
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

    @RequestMapping(value = "/userBudget/status", method = RequestMethod.GET)
    public ModelAndView budgetStatus(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("user-budget-status");
        return view;
    }

    @RequestMapping(value = "/userBudget/categoriesStatus", method = RequestMethod.GET)
    public ModelAndView budgetCategoriesStatus(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
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
        ModelAndView view = new ModelAndView("user-budget-categories-status");
        view.addObject("categories", categories);
        view.addObject("paymentTypes", paymentTypes);
        return view;
    }

    @RequestMapping(value = "/userBudget/recurring/add", method = RequestMethod.GET)
    public ModelAndView recurringPayments(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<RecurringType> recurringTypes = context
                .getRecurringTypeSet()
                .GetAll();
        List<PaymentCategory> caregories = context
                .getPaymentCategorySet()
                .GetAllActiveCategoriesForUser(user.getId());
        ModelAndView view = new ModelAndView("user-budget-recurring-payment-add");
        view.addObject("recTypes", recurringTypes);
        view.addObject("categories", caregories);
        return view;
    }

    @RequestMapping(value = "/userBudget/recurring/add", method = RequestMethod.POST)
    public ModelAndView recurringPayments(ModelMap map,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RecurringBudgetPaymentAddModel params) throws BasicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (params.getRecurringPaymentCategory() == null) {
                throw new ValidationException("Recurring payment add error: required fields not filled.");
            }
            if (params.getRecurringPaymentAmount() < 0) {
                params.setRecurringPaymentAmount(0);
            }
            if (params.getRecurringPaymentPeriodsCount() < 1) {
                params.setRecurringPaymentAmount(1);
            }
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            RecurringBudgetPaymentData data = new RecurringBudgetPaymentData();
            data.setActive(true);
            data.setAmount(params.getRecurringPaymentAmount());
            data.setDescription(params.getRecurringPaymentDescription());
            data.setFinished(false);
            data.setFinishedDate(null);
            data.setPaymentCategoryId(params.getRecurringPaymentCategory());
            data.setPeriods(params.getRecurringPaymentPeriodsCount());
            data.setRecurringType(params.getRecurringPaymentRecurringType());
            data.setStartDate(params.getRecurringPaymentPeriodStart());
            data.setTitle(params.getRecurringPaymentName());
            data.setUserId(user.getId());
            UUID id = context
                    .getRecurringBudgetPaymentSet()
                    .Add(data);
            ModelAndView view = null;
            String buttonSubmitted = request.getParameter("submit-button");
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/userBudget/recurring/add");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/userBudget/recurring/add");
                    break;
                case "3":
                    view = new ModelAndView("redirect:/userBudget/recurring/add");
                    break;
            }
            return view;
        } catch (ValidationException ex) {
            map.put("errorMessage", "Моля въведете всички задължителни полета.");
            ModelAndView view = new ModelAndView("user-budget-recurring-payment-add");
            return view;
        }
    }
}
