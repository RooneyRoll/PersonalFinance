/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.utils.recuringPayments;

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
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Months;
import org.joda.time.Weeks;
import org.joda.time.Years;

/**
 *
 * @author mihail
 */
public class RecuringPaymentsOverviewManager {
    public static RecuringOverviewResultModel generatePaymentsOverviewByAmount(RecuringOverviewByAmountModel ruleOverviewData) {
        Double singlePeriodAmount = RecuringPaymentsOverviewManager
                .calculateSinglePeriodAmount(ruleOverviewData.getPaymentInitialAmount(), ruleOverviewData.getPaymentFinalAmount(), ruleOverviewData.getPeriodsCount());
        Integer missPerPeriods = ruleOverviewData.getMissPerPeriods();
        List<RecuringOverviewResultPaymentModel> payments = RecuringPaymentsOverviewManager
                .buildPaymentsOverview(
                        ruleOverviewData.getPaymentRecuringType(),
                        missPerPeriods,
                        (ruleOverviewData.getPeriodsCount() - 1),
                        ruleOverviewData.getPaymentStartDate(),
                        singlePeriodAmount,
                        null
                );
        Long finishDate = payments.get(payments.size() - 1).getDateRepresent();
        RecuringOverviewResultModel overviewResponceObject = new RecuringOverviewResultModel(
                missPerPeriods,
                ruleOverviewData.getPeriodsCount(),
                ruleOverviewData.getPaymentName(),
                ruleOverviewData.getPaymentDescription(),
                ruleOverviewData.getPaymentFinalAmount(),
                ruleOverviewData.getPaymentStartDate().getTime(),
                finishDate,
                ruleOverviewData.getPaymentCategory(),
                ruleOverviewData.getPaymentRecuringType(),
                0.0,
                singlePeriodAmount
        );
        overviewResponceObject.setPayments(payments);
        return overviewResponceObject;
    }

    public static RecuringOverviewResultModel generatePaymentsOverviewByPeriod(RecuringOverviewByPeriodModel ruleOverviewData) {
        Integer periods = RecuringPaymentsOverviewManager
                .getPeriodsInInterval(ruleOverviewData.getPaymentRecuringType(), ruleOverviewData.getPaymentStartDate(), ruleOverviewData.getPaymentFinishDate());
        Double totalAmount = (periods + 1) * ruleOverviewData.getPaymentSinglePeriodAmount();
        List<RecuringOverviewResultPaymentModel> payments = RecuringPaymentsOverviewManager
                .buildPaymentsOverview(ruleOverviewData.getPaymentRecuringType(), ruleOverviewData.getMissPerPeriods(), periods, ruleOverviewData.getPaymentStartDate(), ruleOverviewData.getPaymentSinglePeriodAmount(), ruleOverviewData.getPaymentFinishDate());
        RecuringOverviewResultModel overviewResponceObject = new RecuringOverviewResultModel(
                ruleOverviewData.getMissPerPeriods(),
                periods,
                ruleOverviewData.getPaymentName(),
                ruleOverviewData.getPaymentDescription(),
                totalAmount,
                ruleOverviewData.getPaymentStartDate().getTime(),
                ruleOverviewData.getPaymentFinishDate().getTime(),
                ruleOverviewData.getPaymentCategory(),
                ruleOverviewData.getPaymentRecuringType(),
                0.0,
                ruleOverviewData.getPaymentSinglePeriodAmount()
        );
        overviewResponceObject.setPayments(payments);
        return overviewResponceObject;
    }

    public static Double calculateSinglePeriodAmount(Double initialAmount, Double targetAmount, Integer periods) {
        Double singlePeriodAmount;
        DecimalFormat decimalFormat = new DecimalFormat("#.##", new DecimalFormatSymbols(Locale.US));
        targetAmount = targetAmount - initialAmount;
        singlePeriodAmount = targetAmount / periods;
        singlePeriodAmount = Double.valueOf(decimalFormat.format(singlePeriodAmount));
        return singlePeriodAmount;
    }

    private static List<RecuringOverviewResultPaymentModel> buildPaymentsOverview(Integer recuringType, Integer missPerPeriods, Integer periods, Date startPaymentDate, Double singlePeriodAmount, Date finalDate) {
        List<RecuringOverviewResultPaymentModel> paymentsList = new ArrayList<>();
        paymentsList.add(RecuringPaymentsOverviewManager.buildSinglePaymentModel(startPaymentDate, singlePeriodAmount)
        );
        for (int i = 1; i <= periods; i++) {
            startPaymentDate = RecuringPaymentsOverviewManager.getNextPaymentDate(recuringType, missPerPeriods, startPaymentDate);
            if (finalDate != null && finalDate.compareTo(startPaymentDate) < 0) {
                break;
            }
            paymentsList.add(RecuringPaymentsOverviewManager.buildSinglePaymentModel(startPaymentDate, singlePeriodAmount)
            );
        }
        return paymentsList;
    }

    private static Date getNextPaymentDate(Integer recuringType, Integer missPerPeriods, Date lastDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(lastDate);
        if (Objects.equals(recuringType, RecurringTypes.Daily.getValue())) {
            cal.add(Calendar.DATE, 1 + missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Weekly.getValue())) {
            cal.add(Calendar.DATE, 7 * missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Monthly.getValue())) {
            cal.add(Calendar.MONTH, 1 + missPerPeriods);
        }
        if (Objects.equals(recuringType, RecurringTypes.Yearly.getValue())) {
            cal.add(Calendar.YEAR, 1 + missPerPeriods);
        }
        return cal.getTime();
    }

    public static Integer getPeriodsInInterval(Integer recuringType, Date startDate, Date endDate) {
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

    private static RecuringOverviewResultPaymentModel buildSinglePaymentModel(Date paymentDate, Double amount) {
        return new RecuringOverviewResultPaymentModel(paymentDate.getTime(), amount);
    }
}
