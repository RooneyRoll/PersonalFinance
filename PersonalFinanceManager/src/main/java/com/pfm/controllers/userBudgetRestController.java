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
import java.util.UUID;
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
            Date budgetDate = format.parse(params.getMonth() + "/" + params.getYear());
            UserBudget budget = context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), budgetDate);
            categories = context.
                    getCategoryDetailSet().
                    GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            //budget not found
        }
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        return json;
    }

    @RequestMapping(value = "/budget/plannedVsSpentCategories", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String plannedVsSpentCategories(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        Gson gson = new Gson();
        List<BudgetCategoriesPlannedVsSpentResult> result = new ArrayList<>();
        try {
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            Date budgetDate = format.parse(params.getMonth() + "/" + params.getYear());
            result = appendCategoryPlannedVsSpentResult(budgetDate, result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            result = appendEmptyCategoryPlannedVsSpentResult(result);
            String json = gson.toJson(result);
            return json;
        }
        String json = gson.toJson(result);
        return json;
    }

    @RequestMapping(value = "/budget/plannedVsSpent", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String plannedVsSpent(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        Gson gson = new Gson();
        List<BudgetPlannedVsSpentResultObject> result = new ArrayList<>();
        try {
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            Date budgetDate = format.parse(params.getMonth() + "/" + params.getYear());
            result = appendPlannedVsSpentResult(budgetDate,result);
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            result = appendEmptyPlannedVsSpentResult(result);
            String json = gson.toJson(result);
            return json;
        }
        String json = gson.toJson(result);
        return json;
    }
    
    private double calculatePercentage(double planned, double actual) {
        double percents;
        if (planned != 0) {
            percents = (actual / planned) * 100;
        } else {
            percents = 100;
        }
        return percents;
    }
    
    private List<BudgetPlannedVsSpentResultObject> appendPlannedVsSpentResult(Date budgetDate, List<BudgetPlannedVsSpentResultObject> result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        UserBudget budget = context.getUserBudgetSet()
                .getBudgetByDateAndUserId(user.getId(), budgetDate);
        List<PaymentType> types = context.getPaymentTypeSet().GetAll();
        for (PaymentType type : types) {
            double planned = 0;
            double spent = 0;
            BudgetPlannedVsSpentResultObject resultObject = new BudgetPlannedVsSpentResultObject();
            resultObject.setPaymentTypeName(type.getName());
            resultObject.setPaymentType(type.getId());
            List<CategoryBudget> catBudget = context.getCategoryDetailSet()
                    .GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
            for (CategoryBudget categoryBudget : catBudget) {
                PaymentCategory category = context.getPaymentCategorySet().GetById(categoryBudget.getCategoryId());
                Integer categoryId = category.getType();
                Integer categoryType = type.getId();
                boolean equals = categoryId.equals(categoryType);
                if (equals) {
                    planned = planned + categoryBudget.getAmount();
                    List<Payment> payments = context.
                            getPaymentSet().
                            getAllActiveAndConfirmedPaymentsByPaymentCategoryAndMonth(categoryBudget.getCategoryId(), budgetDate);
                    for (Payment payment : payments) {
                        spent = spent + payment.getAmount();
                    }
                }
            }
            resultObject.setActual(spent);
            resultObject.setPlanned(planned);
            result.add(resultObject);
        }
        return result;
    }

    private List<BudgetPlannedVsSpentResultObject> appendEmptyPlannedVsSpentResult(List<BudgetPlannedVsSpentResultObject> result) {
        IpfmContext context = pfmContext.getInstance();
        List<PaymentType> types = context.getPaymentTypeSet().GetAll();
        for (PaymentType type : types) {
            BudgetPlannedVsSpentResultObject resultObject = new BudgetPlannedVsSpentResultObject();
            resultObject.setPaymentTypeName(type.getName());
            resultObject.setPaymentType(type.getId());
            resultObject.setActual(0);
            resultObject.setPlanned(0);
            result.add(resultObject);
        }
        return result;
    }

    private List<BudgetCategoriesPlannedVsSpentResult> appendEmptyCategoryPlannedVsSpentResult(List<BudgetCategoriesPlannedVsSpentResult> result) {
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
        return result;
    }

    private List<BudgetCategoriesPlannedVsSpentResult> appendCategoryPlannedVsSpentResult(Date budgetDate, List<BudgetCategoriesPlannedVsSpentResult> result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        UserBudget budget = context.getUserBudgetSet()
                .getBudgetByDateAndUserId(user.getId(), budgetDate);
        List<PaymentType> types = context.getPaymentTypeSet().GetAll();
        for (PaymentType type : types) {
            List<CategoryBudget> catBudgetsForType = context.getCategoryDetailSet()
                    .GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
            for (CategoryBudget categoryBudget : catBudgetsForType) {
                double planned;
                double spent = 0;
                UUID categoryId = categoryBudget.getCategoryId();
                PaymentCategory category = context.getPaymentCategorySet()
                        .GetById(categoryId);
                Integer currentType = type.getId();
                Integer categoryType = category.getType();
                if (currentType.equals(categoryType)) {
                    planned = categoryBudget.getAmount();
                    List<Payment> payments = context.
                            getPaymentSet().
                            getAllActiveAndConfirmedPaymentsByPaymentCategoryAndMonth(categoryBudget.getCategoryId(), budgetDate);
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
        return result;
    }
}
