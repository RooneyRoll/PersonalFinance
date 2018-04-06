/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.categoryDetails;

import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author Admin
 */
public class CategoryBudgetAddModel {
    
    private String amount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fromDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date toDate;
    private UUID categoryId;

    public String getAmount() {
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

    public void setAmount(String amount) {
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
    
    
}
