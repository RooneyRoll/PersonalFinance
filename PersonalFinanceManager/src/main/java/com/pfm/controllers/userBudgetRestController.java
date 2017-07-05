/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.data.entities.Payment;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.data.entities.UserBudget;
import com.pfm.models.budgetService.BudgetCategoriesPlannedVsSpentResult;
import com.pfm.models.budgetService.BudgetParamObject;
import com.pfm.models.budgetService.BudgetPlannedVsSpentResultObject;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Misho
 */
@RestController
public class userBudgetRestController {

    private double calculatePercentage(double planned, double actual) {
       double percents;
        if (planned != 0) {
            percents = (actual / planned) * 100;
        }else{
            percents = 100;
        }
        return percents;
    }

    @RequestMapping(value = "/budget", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String budgetData(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        List<CategoryBudget> categories = new ArrayList<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            UserBudget budget = context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), format.parse(params.getMonth() + "/" + params.getYear()));
            categories = context.
                    getCategoryDetailSet().
                    GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            System.out.println("budget not found");
        }
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        return json;
    }

    @RequestMapping(value = "/budget/plannedVsSpent", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String plannedVsSpent(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        Gson gson = new Gson();
        List<BudgetPlannedVsSpentResultObject> result = new ArrayList<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            UserBudget budget = context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), format.parse(params.getMonth() + "/" + params.getYear()));
            List<PaymentType> types = context.getPaymentTypeSet().GetAll();
            for (PaymentType type : types) {
                BudgetPlannedVsSpentResultObject resultObject = new BudgetPlannedVsSpentResultObject();
                resultObject.setPaymentType(type.getName());
                double planned = 0;
                double spent = 0;
                List<CategoryBudget> catBudget = context.getCategoryDetailSet()
                        .GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
                for (CategoryBudget categoryBudget : catBudget) {
                    PaymentCategory category = context.getPaymentCategorySet().GetById(categoryBudget.getCategoryId());
                    Integer categoryId = category.getType();
                    Integer categoryType = type.getId();
                    boolean equals = categoryId.equals(categoryType);
                    if (equals) {
                        planned = planned + categoryBudget.getAmount();
                        Date date = format.parse(params.getMonth() + "/" + params.getYear());
                        List<Payment> payments = context.
                                getPaymentSet().
                                getAllActivePaymentsByPaymentCategoryAndMonth(categoryBudget.getCategoryId(), date);
                        for (Payment payment : payments) {
                            spent = spent + payment.getAmount();
                        }
                    }
                }
                resultObject.setActual(spent);
                resultObject.setPlanned(planned);
                result.add(resultObject);
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            IpfmContext context = pfmContext.getInstance();
            List<PaymentType> types = context.getPaymentTypeSet().GetAll();
            for (PaymentType type : types) {
                BudgetPlannedVsSpentResultObject resultObject = new BudgetPlannedVsSpentResultObject();
                resultObject.setPaymentType(type.getName());
                resultObject.setActual(0);
                resultObject.setPlanned(0);
                result.add(resultObject);
            }
            String json = gson.toJson(result);
            return json;
        }
        String json = gson.toJson(result);
        return json;
    }

    @RequestMapping(value = "/budget/plannedVsSpentCategories", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String plannedVsSpentCategories(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        Gson gson = new Gson();
        List<BudgetCategoriesPlannedVsSpentResult> result = new ArrayList<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            UserBudget budget = context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), format.parse(params.getMonth() + "/" + params.getYear()));
            List<PaymentType> types = context.getPaymentTypeSet().GetAll();
            for (PaymentType type : types) {
                List<CategoryBudget> catBudget = context.getCategoryDetailSet()
                        .GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
                for (CategoryBudget categoryBudget : catBudget) {
                    PaymentCategory category = context.getPaymentCategorySet().GetById(categoryBudget.getCategoryId());
                    Integer categoryId = category.getType();
                    Integer categoryType = type.getId();
                    double planned;
                    double spent = 0;
                    boolean equals = categoryId.equals(categoryType);
                    if (equals) {
                        planned = categoryBudget.getAmount();
                        Date date = format.parse(params.getMonth() + "/" + params.getYear());
                        List<Payment> payments = context.
                                getPaymentSet().
                                getAllActivePaymentsByPaymentCategoryAndMonth(categoryBudget.getCategoryId(), date);
                        for (Payment payment : payments) {
                            spent = spent + payment.getAmount();
                        }
                        BudgetCategoriesPlannedVsSpentResult resultObject = new BudgetCategoriesPlannedVsSpentResult();
                        resultObject.setPaymentType(type.getName());
                        resultObject.setCategoryName(category.getName());
                        resultObject.setActual(spent);
                        resultObject.setPlanned(planned);
                        resultObject.setCategoryId(category.getId());
                        resultObject.setCatType(categoryType);
                        resultObject.setPercents(calculatePercentage(planned, spent));
                        result.add(resultObject);
                    }
                }
            }
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            List<PaymentCategory> catList = context.
                    getPaymentCategorySet().
                    GetAllActiveCategoriesForUser(user.getId());
            for (PaymentCategory paymentCategory : catList) {
                BudgetCategoriesPlannedVsSpentResult resultObject = new BudgetCategoriesPlannedVsSpentResult();
                resultObject.setCatType(paymentCategory.getType());
                resultObject.setCategoryName(paymentCategory.getName());
                resultObject.setActual(0);
                resultObject.setPlanned(0);
                resultObject.setCategoryId(paymentCategory.getId());
                resultObject.setPercents(0);
                result.add(resultObject);
                
            }
            String json = gson.toJson(result);
            return json;
        }
        String json = gson.toJson(result);
        return json;
    }
}
