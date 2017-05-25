/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.budgetService;

/**
 *
 * @author Misho
 */
public class BudgetPlannedVsSpentResultObject {
    private String paymentType;
    private double planned;
    private double actual;

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public double getPlanned() {
        return planned;
    }

    public void setPlanned(double planned) {
        this.planned = planned;
    }

    public double getActual() {
        return actual;
    }

    public void setActual(double spent) {
        this.actual = spent;
    }
    
    
}
