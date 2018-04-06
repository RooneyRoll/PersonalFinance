/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.paymentCategory;

import com.pfm.exceptions.ValidationException;

/**
 *
 * @author mihail
 */
public class PaymentCategoryAddModel {

    private String categoryName;
    private String categoryDescription;
    private Integer categoryType;

    public Integer getCategoryType() {
        return categoryType;
    }

    public String getCategoryName() throws ValidationException {
        if ("".equals(categoryName)) {
            throw new ValidationException("PaymentCategoryAddException: category name cannot be empty.");
        }
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }
    
}
