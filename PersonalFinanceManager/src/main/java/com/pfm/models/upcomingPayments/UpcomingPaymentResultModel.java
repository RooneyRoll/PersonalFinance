/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.upcomingPayments;

/**
 *
 * @author mihail
 */
public class UpcomingPaymentResultModel {
    private final String description;
    private final Double amount;
    private final boolean recuring;

    public UpcomingPaymentResultModel(String description, Double amount, boolean recuring) {
        this.description = description;
        this.amount = amount;
        this.recuring = recuring;
    }

    public String getDescription() {
        return description;
    }

    public Double getAmount() {
        return amount;
    }

    public boolean isRecuring() {
        return recuring;
    }
    
    
}
