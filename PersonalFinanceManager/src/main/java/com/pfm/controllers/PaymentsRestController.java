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
import com.pfm.models.payment.PaymentRestParamObject;
import com.pfm.models.payment.PaymentsForMonthResultObject;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
public class PaymentsRestController {

    @RequestMapping(value = "/getPayments", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String payments(HttpServletRequest request, @RequestBody PaymentRestParamObject params) {
        Gson gson = new Gson();
        List<PaymentsForMonthResultObject> result = new ArrayList<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            Date date = format.parse(params.getMonth() + "/" + params.getYear());
            List<PaymentType> types = context.getPaymentTypeSet().GetAll();
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            List<CategoryBudget> catBudgetList = new ArrayList<>();
            try {
                UserBudget budget = context.getUserBudgetSet()
                        .getBudgetByDateAndUserId(user.getId(), format.parse(params.getMonth() + "/" + params.getYear()));
                catBudgetList = context.getCategoryDetailSet()
                        .GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
            } catch (EntityNotFoundException notFoundExc) {

            }
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            int minDay = cal.getActualMinimum(Calendar.DAY_OF_MONTH) - 1;
            for (PaymentType type : types) {
                PaymentsForMonthResultObject resultObject = new PaymentsForMonthResultObject(maxDay);
                PaymentsForMonthResultObject budgetForMonthObject = new PaymentsForMonthResultObject(maxDay);
                resultObject.setPaymentType(type.getName());
                budgetForMonthObject.setBudget(true);
                budgetForMonthObject.setPaymentType(type.getName());
                double total = 0;
                double budgetLimit = 0;
                int month = cal.get(Calendar.DAY_OF_MONTH) + 1;

                List<PaymentCategory> categories = context
                        .getPaymentCategorySet()
                        .GetAllActiveCategoriesForUserByPaymentTypeId(user.getId(), type.getId());
                for (PaymentCategory category : categories) {
                    for (CategoryBudget categoryBudget : catBudgetList) {
                        if (categoryBudget.getCategoryId().equals(category.getId())) {
                            budgetLimit = budgetLimit + categoryBudget.getAmount();
                        }
                    }
                    budgetForMonthObject.setBudgetValues(budgetLimit, maxDay);
                    List<Payment> paymentsForCategory = context
                            .getPaymentSet()
                            .getAllActivePaymentsByPaymentCategoryAndMonth(category.getId(), date);
                    for (Payment payment : paymentsForCategory) {
                        total = total + payment.getAmount();
                        cal.setTime(payment.getDate());
                        int day = cal.get(Calendar.DAY_OF_MONTH);
                        resultObject.setAt(day - 1, total, month);
                    }
                }
                result.add(budgetForMonthObject);
                result.add(resultObject);
            }
            String json = gson.toJson(result);
            return json;
        } catch (ParseException ex) {
            String json = gson.toJson(result);
            return json;
        }
    }
}
