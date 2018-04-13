/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.upcomingPayments;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihail
 */
public class UpcomingPaymentCategoryResultModel {
    private final String category;
    private final Integer categoryType;
    
    private List<UpcomingPaymentResultModel> payments = new ArrayList<>();

    public UpcomingPaymentCategoryResultModel(String category, Integer categoryType) {
        this.category = category;
        this.categoryType = categoryType;
    }
    
    public String getCategory() {
        return category;
    }

    public Integer getCategoryType() {
        return categoryType;
    }
    
    public List<UpcomingPaymentResultModel> getPayments() {
        return payments;
    }
    
    public void addPayment(UpcomingPaymentResultModel payment){
        this.payments.add(payment);
    }
}
