/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.data;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author mihail
 */
public class RecurringBudgetPaymentData {
    public int recurringType;
    public int periods;
    public boolean active;
    public Date startDate;
    public double amount;
    public UUID userId;
    public UUID paymentCategoryId;

    public int getRecurringType() {
        return recurringType;
    }

    public void setRecurringType(int recurringType) {
        this.recurringType = recurringType;
    }

    public int getPeriods() {
        return periods;
    }

    public void setPeriods(int periods) {
        this.periods = periods;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public UUID getPaymentCategoryId() {
        return paymentCategoryId;
    }

    public void setPaymentCategoryId(UUID paymentCategoryId) {
        this.paymentCategoryId = paymentCategoryId;
    }
}
