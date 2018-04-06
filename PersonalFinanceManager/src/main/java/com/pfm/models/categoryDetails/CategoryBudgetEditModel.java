/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.categoryDetails;

import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Admin
 */
public class CategoryBudgetEditModel {
        
    private double amount;
    private Date fromDate;
    private Date toDate;
    private UUID categoryId;
    private String isActive;

    public double getAmount() {
        return amount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public UUID getCategoryId() {
        return categoryId;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public void setCategoryId(UUID categoryId) {
        this.categoryId = categoryId;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }
    
}
