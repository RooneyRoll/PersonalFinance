/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.requestObjects.paymentCategory;

import com.pfm.exceptions.ValidationException;

/**
 *
 * @author mihail
 */
public class PaymentCategoryAddRequestObject {
    public String categoryName;
    public String categoryDescription;

    public String getCategoryName() throws ValidationException {
        if("".equals(categoryName))
            throw new ValidationException("PaymentCategoryAddException: category name cannot be empty.");
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryDescription() {
        return categoryDescription;
    }

    public void setCategoryDescription(String categoryDescription) {
        this.categoryDescription = categoryDescription;
    }
    
    
}
