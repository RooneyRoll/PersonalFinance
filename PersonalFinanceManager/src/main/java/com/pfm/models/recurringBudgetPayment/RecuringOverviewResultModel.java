/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.recurringBudgetPayment;


/**
 *
 * @author mihail
 */
public class RecuringOverviewResultModel {
    private final Long dateRepresent;
    private final Double amount;

    public RecuringOverviewResultModel(Long dateRepresent, Double amount) {
        this.dateRepresent = dateRepresent;
        this.amount = amount;
    }
    
    public Long getDateRepresent() {
        return dateRepresent;
    }

    public Double getAmount() {
        return amount;
    }
}
