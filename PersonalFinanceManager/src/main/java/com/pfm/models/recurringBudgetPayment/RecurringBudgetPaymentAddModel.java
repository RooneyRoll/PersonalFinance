/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.recurringBudgetPayment;

import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Misho
 */
public class RecurringBudgetPaymentAddModel {
    
    public Integer missPerPeriods;
    public UUID paymentCategory;
    public String paymentDescription;
    public Double paymentFinalAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date paymentFinishDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date paymentStartDate;
    public Double paymentInitialAmount;
    public String paymentName;
    public Integer paymentRecuringType;
    public Double paymentSinglePeriodAmount;
    public Integer periodsCount;

    public Integer getMissPerPeriods() {
        return missPerPeriods;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public Double getPaymentFinalAmount() {
        return paymentFinalAmount;
    }

    public Date getPaymentFinishDate() {
        return paymentFinishDate;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public Double getPaymentInitialAmount() {
        return paymentInitialAmount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public Integer getPaymentRecuringType() {
        return paymentRecuringType;
    }

    public Double getPaymentSinglePeriodAmount() {
        return paymentSinglePeriodAmount;
    }

    public Integer getPeriodsCount() {
        return periodsCount;
    }

    public void setMissPerPeriods(Integer missPerPeriods) {
        this.missPerPeriods = missPerPeriods;
    }

    public void setPaymentCategory(UUID paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public void setPaymentFinalAmount(Double paymentFinalAmount) {
        this.paymentFinalAmount = paymentFinalAmount;
    }

    public void setPaymentFinishDate(Date paymentFinishDate) {
        this.paymentFinishDate = paymentFinishDate;
    }

    public void setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public void setPaymentInitialAmount(Double paymentInitialAmount) {
        this.paymentInitialAmount = paymentInitialAmount;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public void setPaymentRecuringType(Integer paymentRecuringType) {
        this.paymentRecuringType = paymentRecuringType;
    }

    public void setPaymentSinglePeriodAmount(Double paymentSinglePeriodAmount) {
        this.paymentSinglePeriodAmount = paymentSinglePeriodAmount;
    }

    public void setPeriodsCount(Integer periodsCount) {
        this.periodsCount = periodsCount;
    }
}
