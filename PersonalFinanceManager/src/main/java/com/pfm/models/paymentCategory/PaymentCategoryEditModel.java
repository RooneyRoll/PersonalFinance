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
public class PaymentCategoryEditModel {

    private String categoryName;
    private String categoryDescription;
    private String categoryActive;
    private Integer categoryType;

    public String getCategoryName() throws ValidationException {
        if ("".equals(categoryName)) {
            throw new ValidationException("PaymentCategoryEditException: category name cannot be empty.");
        }
        return categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public String getCategoryActive() {
        return categoryActive;
    }

    public Integer getCategoryType() {
        return categoryType;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }

    public void setCategoryActive(String categoryActive) {
        this.categoryActive = categoryActive;
    }

    public void setCategoryType(Integer categoryType) {
        this.categoryType = categoryType;
    }
    
}
