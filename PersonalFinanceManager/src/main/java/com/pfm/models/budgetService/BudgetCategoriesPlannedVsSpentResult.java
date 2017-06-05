/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.budgetService;

import java.util.UUID;

/**
 *
 * @author Misho
 */
public class BudgetCategoriesPlannedVsSpentResult {
    private String paymentType;
    private String categoryName;
    private double planned;
    private double actual;
    private double percents;
    private UUID categoryId;
    private Integer catType;

    public Integer getCatType() {
        return catType;
    }

    public void setCatType(Integer catType) {
        this.catType = catType;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }
    
    public double getPercents() {
        return percents;
    }

    public void setPercents(double percents) {
        this.percents = percents;
    }
    
    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    } 
    
    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
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

    public void setActual(double actual) {
        this.actual = actual;
    }
    
    
}
