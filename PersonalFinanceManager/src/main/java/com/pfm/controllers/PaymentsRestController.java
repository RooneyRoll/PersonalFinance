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
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date start;
        Date end;
        try {
            start = format.parse("2017/11/01");
            end = format.parse("2017/11/29");
            this.buildStatsJsonForInterval(start, end);
        } catch (ParseException ex) {
            //
        }
        return "";

        /*Gson gson = new Gson();
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
                System.out.println(notFoundExc.getMessage());
            }
            int maxDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            for (PaymentType type : types) {
                PaymentsForMonthResultObject resultObject = new PaymentsForMonthResultObject(maxDay);
                PaymentsForMonthResultObject budgetForMonthObject = new PaymentsForMonthResultObject(maxDay);
                resultObject.setPaymentType(type.getName());

                budgetForMonthObject.setBudget(true);
                budgetForMonthObject.setPaymentType(type.getName());
                List<Payment> payments = context
                        .getPaymentSet()
                        .getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndMonth(user.getId(), type.getId(), date);
                List<PaymentCategory> categories = context
                        .getPaymentCategorySet()
                        .GetAllActiveCategoriesForUserByPaymentTypeId(user.getId(), type.getId());
                double total = 0;
                double budgetLimit = 0;
                int month = cal.get(Calendar.MONTH) + 1;
                for (Payment payment : payments) {
                    total = total + payment.getAmount();
                    cal.setTime(payment.getDate());
                    int day = cal.get(Calendar.DAY_OF_MONTH);
                    resultObject.setAt(day - 1, total, month);
                }
                for (PaymentCategory category : categories) {
                    for (CategoryBudget categoryBudget : catBudgetList) {
                        if (categoryBudget.getCategoryId().equals(category.getId())) {
                            budgetLimit = budgetLimit + categoryBudget.getAmount();
                        }
                    }
                    budgetForMonthObject.setBudgetValues(budgetLimit, maxDay);
                }
                result.add(budgetForMonthObject);
                result.add(resultObject);
            }
            String json = gson.toJson(result);
            return json;
        } catch (ParseException ex) {
            String json = gson.toJson(result);
            return json;
        }*/
    }

    private void buildStatsJsonForInterval(Date start, Date end) {
        int daysBetween = getDaysBetweenDates(start, end);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context.getUserSet().GetByUserName(auth.getName());
        List<PaymentType> types = context.getPaymentTypeSet().GetAll();
        for (PaymentType type : types) {
            List<PaymentCategory> categories = context.getPaymentCategorySet().GetAllActiveCategoriesForUserByPaymentTypeId(user.getId(), type.getId());
            for (PaymentCategory category : categories) {
                List<Payment> payments = context.getPaymentSet().getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndInterval(user.getId(), type.getId(), start, end);
                buildValuesStructure(payments);
            }
        }
    }

    private void buildValuesStructure(List<Payment> payments) {
        HashMap<String, Double[]> map = new HashMap<String, Double[]>();
        Date prevDate = new Date();
        Double total = 0.0;
        for (Payment payment : payments) {
            int month = determineMonthByDate(payment.getDate());
            int year = determineYearByDate(payment.getDate());
            String key = year + "|" + month;
            if (map.get(key) == null) {
                int length = determineRemainingDaysToMonth(payment.getDate());
                Double amount = payment.getAmount();
                total = amount;
                Double[] values = new Double[length];
                Arrays.fill(values, 0.0);
                values[0] = total;
                map.put(key, values);
                prevDate = payment.getDate();
            } else {
                int length = determineRemainingDaysToMonth(payment.getDate());
                int currentIndex = getDaysBetweenDates(prevDate, payment.getDate());
                Double amount = payment.getAmount();
                total = total + amount;
                Double[] values = map.get(key);
                values[currentIndex] = total;
                map.put(key, values);
                prevDate = payment.getDate();
            }
        }
        for (Map.Entry<String, Double[]> entry : map.entrySet()) {
            String key = entry.getKey();
            Double[] value = entry.getValue();
            for (Double dbl : value) {
                System.out.println(dbl);

            }
            // do what you have to do here
            // In your case, another loop.
        }
    }

    private int determineMonthByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        return month;
    }

    private int determineYearByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return year;
    }

    private int determineDayByDate(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return day;
    }

    private int determineRemainingDaysToMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int max = cal.getMaximum(Calendar.DAY_OF_MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH) + 1;
        return max - day;
    }

    private int getDaysBetweenDates(Date start, Date end) {
        Calendar cal1 = new GregorianCalendar();
        Calendar cal2 = new GregorianCalendar();
        cal1.setTime(start);
        cal2.setTime(end);
        int daysBetween = cal2.get(Calendar.DAY_OF_YEAR) - cal1.get(Calendar.DAY_OF_YEAR);
        return daysBetween;
    }
}
