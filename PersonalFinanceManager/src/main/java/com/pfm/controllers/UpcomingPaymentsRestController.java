/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.entities.Payment;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.RecurringBudgetPayment;
import com.pfm.data.entities.User;
import com.pfm.enums.RecurringTypes;
import com.pfm.models.upcomingPayments.UpcomingPaymentCategoryResultModel;
import com.pfm.models.upcomingPayments.UpcomingPaymentResultModel;
import com.pfm.models.upcomingPayments.UpcomingPaymentsPreviewModel;
import com.pfm.models.upcomingPayments.UpcomingPaymentsResultModel;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;
import org.joda.time.Days;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mihail
 */
@RestController
public class UpcomingPaymentsRestController extends BaseController {

    @RequestMapping(value = "/getUpcomingPayments", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String payments(HttpServletRequest request, @RequestBody UpcomingPaymentsPreviewModel data) {
        Date start = data.getStartDate();
        Date end = data.getEndDate();
        List<UpcomingPaymentsResultModel> model = this.generateUpcomingPayments(start, end);
        return "";
    }

    private Date getNextPaymentDate(Integer recuringType, Integer missPerPeriods, Date lastDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDate);
        if (Objects.equals(recuringType, RecurringTypes.Daily.getValue())) {
            cal.add(Calendar.DATE, 1 + missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Weekly.getValue())) {
            cal.add(Calendar.DATE, 7 + missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Monthly.getValue())) {
            cal.add(Calendar.MONTH, 1 + missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Yearly.getValue())) {
            cal.add(Calendar.YEAR, 1 + missPerPeriods);
        }
        return cal.getTime();
    }
    
    private UpcomingPaymentResultModel getRecuringPaymentsByRule(RecurringBudgetPayment paymentsRule,Date date){
        Integer periods = paymentsRule.getPeriods();
        Integer coveredPeriods = paymentsRule.getCoveredPeriods();
        Date recStartDate = paymentsRule.getStartDate();
        for (int period = 1; period <= periods; period++) {
            if(period > coveredPeriods){
                Integer difference = DateTimeComparator.getDateOnlyInstance().compare(recStartDate, date);
                if(difference != 0){
                    return null;
                }
                    
            }
            recStartDate = getNextPaymentDate(paymentsRule.getRecurringType(), paymentsRule.getMissPerPeriods(), recStartDate);
        }
        return null;
    }
    
    private List<UpcomingPaymentsResultModel> generateUpcomingPayments(Date start, Date end) {
        User user = this.getPfmContext()
                .getUserSet()
                .GetByUserName(this.getAuth().getName());
        UUID userId = user.getId();
        List<UpcomingPaymentsResultModel> payments = new ArrayList<>();
        Integer days = this.getDaysBetweenDates(start, end);
        Date currentDate = start;
        for (int i = 1; i <= days; i++) {
            this.getPaymentsForDate(currentDate, userId);
            currentDate = this.incrementDate(currentDate, days, Calendar.DATE);
        }
        return payments;
    }

    private UpcomingPaymentsResultModel getPaymentsForDate(Date date, UUID userId) {
        Long dateRepresent = date.getTime();
        UpcomingPaymentsResultModel upcomingPayments = new UpcomingPaymentsResultModel(dateRepresent);
        List<PaymentCategory> categories = this.getPfmContext()
                .getPaymentCategorySet()
                .GetAllActiveCategoriesForUser(userId);
        for (PaymentCategory category : categories) {
            UpcomingPaymentCategoryResultModel categoryModel = new UpcomingPaymentCategoryResultModel(category.getName(), category.getType());
            List<UpcomingPaymentResultModel> standardPayments = this.getStandardPayments(category.getId(), date);
            List<UpcomingPaymentResultModel> recuringPayments = this.getRecuringPayments(category.getId(), date);
        }
        return upcomingPayments;
    }

    private List<UpcomingPaymentResultModel> getRecuringPayments(UUID categoryId, Date date) {
        boolean isRecuring = true;
        List<UpcomingPaymentResultModel> upcoming = new ArrayList<>();
        List<RecurringBudgetPayment> recuringRules = this.getPfmContext()
                .getRecurringBudgetPaymentSet()
                .getAllStartedAndNotFinishedAndActiveByCategoryIdAndDate(categoryId, date);
        for (RecurringBudgetPayment recRule : recuringRules) {
           this.getRecuringPaymentsByRule(recRule, date);
        }
        return upcoming;
    }

    private List<UpcomingPaymentResultModel> getStandardPayments(UUID categoryId, Date date) {
        boolean isRecuring = false;
        List<UpcomingPaymentResultModel> upcoming = new ArrayList<>();
        List<Payment> payments = this.getPfmContext()
                .getPaymentSet()
                .getAllNotConfirmedAndActiveByCategoryAndDate(categoryId, date);
        for (Payment payment : payments) {
            UpcomingPaymentResultModel paymentModel = new UpcomingPaymentResultModel(
                    payment.getDescription(),
                    payment.getAmount(),
                    isRecuring
            );
            upcoming.add(paymentModel);
        }
        return upcoming;
    }

    private Date incrementDate(Date date, Integer periods, Integer calendarType) {
        if (calendarType == null) {
            calendarType = Calendar.DATE;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(calendarType, periods);
        return cal.getTime();
    }

    private Integer getDaysBetweenDates(Date start, Date end) {
        Integer days = 0;
        DateTime startDate = new DateTime(start);
        DateTime endDate = new DateTime(end);
        days = Days.daysBetween(startDate, endDate).getDays();
        return days;
    }
    
}
