/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.recurringBudgetPayment;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mihail
 */
public class RecurringBudgetPaymentAddModel {
    
    public Integer missPerPeriods;
    public Integer periodsCount;
    public String paymentName;
    public String paymentDescription;
    public Double paymentFinalAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date paymentStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date paymentFinishDate;
    public Integer paymentCategory;
    public Integer paymentRecuringType;

    public Integer getMissPerPeriods() {
        return missPerPeriods;
    }

    public void setMissPerPeriods(Integer missPerPeriods) {
        this.missPerPeriods = missPerPeriods;
    }

    public Integer getPeriodsCount() {
        return periodsCount;
    }

    public void setPeriodsCount(Integer periodsCount) {
        this.periodsCount = periodsCount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public Double getPaymentFinalAmount() {
        return paymentFinalAmount;
    }

    public void setPaymentFinalAmount(Double paymentFinalAmount) {
        this.paymentFinalAmount = paymentFinalAmount;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public void setPaymentStartDate(Date paymentStartDate) {
        this.paymentStartDate = paymentStartDate;
    }

    public Date getPaymentFinishDate() {
        return paymentFinishDate;
    }

    public void setPaymentFinishDate(Date paymentFinishDate) {
        this.paymentFinishDate = paymentFinishDate;
    }

    public Integer getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(Integer paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public Integer getPaymentRecuringType() {
        return paymentRecuringType;
    }

    public void setPaymentRecuringType(Integer paymentRecuringType) {
        this.paymentRecuringType = paymentRecuringType;
    }
    
    
    
}
