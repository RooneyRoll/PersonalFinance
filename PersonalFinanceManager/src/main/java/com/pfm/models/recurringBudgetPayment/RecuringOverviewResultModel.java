/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.recurringBudgetPayment;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author mihail
 */
public class RecuringOverviewResultModel {
    private final Integer missPerPeriods;
    private final Integer periodsCount;
    private final String paymentName;
    private final String paymentDescription;
    private final Double paymentFinalAmount;
    private final Long paymentStartDate;
    private final Long paymentFinishDate;
    private final UUID paymentCategory;
    private final Integer paymentRecuringType;
    private final Double paymentInitialAmount;
    private final Double paymentSinglePeriodAmount;
    private List<RecuringOverviewResultPaymentModel> payments = new ArrayList<>();

    public RecuringOverviewResultModel(Integer missPerPeriods, Integer periodsCount, String paymentName, String paymentDescription, Double paymentFinalAmount, Long paymentStartDate, Long paymentFinishDate, UUID paymentCategory, Integer paymentRecuringType, Double paymentInitialAmount, Double paymentSinglePeriodAmount) {
        this.missPerPeriods = missPerPeriods;
        this.periodsCount = periodsCount;
        this.paymentName = paymentName;
        this.paymentDescription = paymentDescription;
        this.paymentFinalAmount = paymentFinalAmount;
        this.paymentStartDate = paymentStartDate;
        this.paymentFinishDate = paymentFinishDate;
        this.paymentCategory = paymentCategory;
        this.paymentRecuringType = paymentRecuringType;
        this.paymentInitialAmount = paymentInitialAmount;
        this.paymentSinglePeriodAmount = paymentSinglePeriodAmount;
    }
    
    public Integer getMissPerPeriods() {
        return missPerPeriods;
    }

    public Integer getPeriodsCount() {
        return periodsCount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public Double getPaymentFinalAmount() {
        return paymentFinalAmount;
    }

    public Long getPaymentStartDate() {
        return paymentStartDate;
    }

    public Long getPaymentFinishDate() {
        return paymentFinishDate;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public Integer getPaymentRecuringType() {
        return paymentRecuringType;
    }

    public Double getPaymentInitialAmount() {
        return paymentInitialAmount;
    }

    public Double getPaymentSinglePeriodAmount() {
        return paymentSinglePeriodAmount;
    }

    public List<RecuringOverviewResultPaymentModel> getPayments() {
        return payments;
    }
    
    public void pushPayment(RecuringOverviewResultPaymentModel payment){
        this.payments.add(payment);
    }

    public void setPayments(List<RecuringOverviewResultPaymentModel> payments) {
        this.payments = payments;
    }
}
