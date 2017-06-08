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
 * @author Misho
 */
public class PaymentData {
    private double amount;
    private Date date;
    private String description;
    private boolean active;
    private UUID category;
    private UUID budgetRecurringPayment;
    private int coveredRecurringPeriods;

    public int getCoveredRecurringPeriods() {
        return coveredRecurringPeriods;
    }

    public void setCoveredRecurringPeriods(int coveredRecurringPeriods) {
        this.coveredRecurringPeriods = coveredRecurringPeriods;
    }

    public UUID getBudgetRecurringPayment() {
        return budgetRecurringPayment;
    }

    public void setBudgetRecurringPayment(UUID budgetRecurringPayment) {
        this.budgetRecurringPayment = budgetRecurringPayment;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public UUID getCategory() {
        return category;
    }

    public void setCategory(UUID category) {
        this.category = category;
    }
}
