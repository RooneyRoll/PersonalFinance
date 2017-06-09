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
 * @author mihail
 */
public class RecurringBudgetPaymentAddModel {
    public String recurringPaymentName;
    public double recurringPaymentAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date recurringPaymentPeriodStart;
    public int recurringPaymentPeriodsCount;
    public String recurringPaymentDescription;
    public UUID recurringPaymentCategory;
    public int recurringPaymentRecurringType;

    public String getRecurringPaymentName() {
        return recurringPaymentName;
    }

    public void setRecurringPaymentName(String recurringPaymentName) {
        this.recurringPaymentName = recurringPaymentName;
    }

    public double getRecurringPaymentAmount() {
        return recurringPaymentAmount;
    }

    public void setRecurringPaymentAmount(double recurringPaymentAmount) {
        this.recurringPaymentAmount = recurringPaymentAmount;
    }

    public Date getRecurringPaymentPeriodStart() {
        return recurringPaymentPeriodStart;
    }

    public void setRecurringPaymentPeriodStart(Date recurringPaymentPeriodStart) {
        this.recurringPaymentPeriodStart = recurringPaymentPeriodStart;
    }

    public int getRecurringPaymentPeriodsCount() {
        return recurringPaymentPeriodsCount;
    }

    public void setRecurringPaymentPeriodsCount(int recurringPaymentPeriodsCount) {
        this.recurringPaymentPeriodsCount = recurringPaymentPeriodsCount;
    }

    public String getRecurringPaymentDescription() {
        return recurringPaymentDescription;
    }

    public void setRecurringPaymentDescription(String recurringPaymentDescription) {
        this.recurringPaymentDescription = recurringPaymentDescription;
    }

    public UUID getRecurringPaymentCategory() {
        return recurringPaymentCategory;
    }

    public void setRecurringPaymentCategory(UUID recurringPaymentCategory) {
        this.recurringPaymentCategory = recurringPaymentCategory;
    }

    public int getRecurringPaymentRecurringType() {
        return recurringPaymentRecurringType;
    }

    public void setRecurringPaymentRecurringType(int recurringPaymentRecurringType) {
        this.recurringPaymentRecurringType = recurringPaymentRecurringType;
    }
}
