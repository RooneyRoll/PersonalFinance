/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.enums.RecurringTypes;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewByAmountModel;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewByPeriodModel;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewResultModel;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewResultPaymentModel;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Misho
 */
@RestController
public class RecurringPaymentsRestController {

    @RequestMapping(value = "/recBySumOverview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String recBySumOverview(HttpServletRequest request, @RequestBody RecuringOverviewByAmountModel data) {
        Date startPaymentDate = data.getPaymentStartDate();
        Integer recuringType = data.getPaymentRecuringType();
        Integer periods = data.getPeriodsCount();
        Double initial = data.getPaymentInitialAmount();
        Double target = data.getPaymentFinalAmount();
        Double singlePeriodAmount = this.calculateSinglePeriodAmount(initial, target, periods);
        Integer missPerPeriods = data.getMissPerPeriods();
        List<RecuringOverviewResultPaymentModel> payments = this.buildPaymentsOverviewResponse(recuringType, missPerPeriods, periods - 1, startPaymentDate, singlePeriodAmount,null);
        Long finishDate = payments.get(payments.size() - 1).getDateRepresent();
        RecuringOverviewResultModel overviewResponceObject = new RecuringOverviewResultModel(
                missPerPeriods,
                periods,
                data.getPaymentName(),
                data.getPaymentDescription(),
                target,
                startPaymentDate.getTime(),
                finishDate,
                data.getPaymentCategory(),
                data.getPaymentRecuringType(),
                0.0,
                singlePeriodAmount
        );
        overviewResponceObject.setPayments(payments);
        Gson gson = new Gson();
        String response = gson.toJson(overviewResponceObject);
        return response;
    }

    @RequestMapping(value = "/recByPeriodOverview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String recByPeriodOverview(HttpServletRequest request, @RequestBody RecuringOverviewByPeriodModel data) {
        Date startPaymentDate = data.getPaymentStartDate();
        Date lastPaymentDate = data.getPaymentFinishDate();
        Double singlePeriodAmount = data.getPaymentSinglePeriodAmount();
        Integer recuringType = data.getPaymentRecuringType();
        Integer periods = this.getPeriodsInInterval(recuringType, startPaymentDate, lastPaymentDate);
        Integer missPerPeriods = data.getMissPerPeriods();
        Double totalAmount = (periods + 1) * singlePeriodAmount;
        List<RecuringOverviewResultPaymentModel> payments = this.buildPaymentsOverviewResponse(recuringType, missPerPeriods, periods, startPaymentDate, singlePeriodAmount,lastPaymentDate);
        RecuringOverviewResultModel overviewResponceObject = new RecuringOverviewResultModel(
                missPerPeriods,
                periods,
                data.getPaymentName(),
                data.getPaymentDescription(),
                totalAmount,
                startPaymentDate.getTime(),
                lastPaymentDate.getTime(),
                data.getPaymentCategory(),
                data.getPaymentRecuringType(),
                0.0,
                singlePeriodAmount
        );
        overviewResponceObject.setPayments(payments);
        Gson gson = new Gson();
        String response = gson.toJson(overviewResponceObject);
        return response;
    }

    private Double calculateSinglePeriodAmount(Double initialAmount, Double targetAmount, Integer periods) {
        Double singlePeriodAmount;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        targetAmount = targetAmount - initialAmount;
        singlePeriodAmount = targetAmount / periods;
        singlePeriodAmount = Double.valueOf(decimalFormat.format(singlePeriodAmount));
        return singlePeriodAmount;
    }

    private List<RecuringOverviewResultPaymentModel> buildPaymentsOverviewResponse(Integer recuringType, Integer missPerPeriods, Integer periods, Date startPaymentDate, Double singlePeriodAmount, Date finalDate) {
        List<RecuringOverviewResultPaymentModel> paymentsList = new ArrayList<>();
        paymentsList.add(this.buildSinglePaymentModel(startPaymentDate, singlePeriodAmount));
        for (int i = 1; i <= periods; i++) {
            startPaymentDate = this.getNextPaymentDate(recuringType, missPerPeriods, startPaymentDate);
            if (finalDate != null && finalDate.compareTo(startPaymentDate) < 0) {
                break;
            }
            paymentsList.add(this.buildSinglePaymentModel(startPaymentDate, singlePeriodAmount));
        }
        return paymentsList;
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

    private Integer getPeriodsInInterval(Integer recuringType, Date startDate, Date endDate) {
        Integer periods = 0;
        DateTime start = new DateTime(startDate);
        DateTime end = new DateTime(endDate);
        if (Objects.equals(recuringType, RecurringTypes.Daily.getValue())) {
            periods = Days.daysBetween(start, end).getDays();
        }
        if (Objects.equals(recuringType, RecurringTypes.Weekly.getValue())) {
            periods = Weeks.weeksBetween(start, end).getWeeks();
        }
        if (Objects.equals(recuringType, RecurringTypes.Monthly.getValue())) {
            periods = Months.monthsBetween(start, end).getMonths();
        }
        if (Objects.equals(recuringType, RecurringTypes.Yearly.getValue())) {
            periods = Years.yearsBetween(start, end).getYears();
        }
        return periods;
    }

    private RecuringOverviewResultPaymentModel buildSinglePaymentModel(Date paymentDate, Double amount) {
        return new RecuringOverviewResultPaymentModel(paymentDate.getTime(), amount);
    }
}
