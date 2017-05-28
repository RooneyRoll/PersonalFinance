/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.payment;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Misho
 */
public class PaymentsForMonthResultObject {

    public String paymentType;
    public Double[] amounts;
    public boolean budget;
    
    public PaymentsForMonthResultObject(int days) {
        this.amounts = new Double[days];
        Arrays.fill(amounts, 0.0);
    }

    public void setBudgetValues(double amount, int maxDay) {
        int size = amounts.length;
        for (int i = 0; i < size; i++) {
            double averageForDay = amount / maxDay;
            amounts[i] = averageForDay * (i + 1);
        }
    }

    public void setAt(int position, Double amount, int paymentMonth) {
        int size = amounts.length;
        Date now = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(now);
        int month = cal.get(Calendar.MONTH) + 1;
        int todayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        for (int i = position; i < size; i++) {
            if (i < todayOfMonth) {
                amounts[i] = amount;
            } else {
                //todo month compare
                amounts[i] = null;
            }
        }
    }

    public boolean isBudget() {
        return budget;
    }

    public void setBudget(boolean budget) {
        this.budget = budget;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Double[] getAmounts() {
        return amounts;
    }

    public void setAmounts(Double[] amounts) {
        this.amounts = amounts;
    }
}
