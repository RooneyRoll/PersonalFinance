/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.paymentCategory;

import com.pfm.exceptions.ValidationException;
import java.util.UUID;

/**
 *
 * @author mihail
 */
public class PaymentCategoryEditModel {

    public String categoryName;
    public String categoryDescription;
    public String categoryActive;
    public UUID categoryType;

    public UUID getCategoryType() {
        return categoryType;
    }

    public void setCategoryType(UUID categoryType) {
        this.categoryType = categoryType;
    }

    public String getCategoryActive() {
        return categoryActive;
    }

    public void setCategoryActive(String categoryActive) {
        this.categoryActive = categoryActive;
    }

    public String getCategoryName() throws ValidationException {
        if ("".equals(categoryName)) {
            throw new ValidationException("PaymentCategoryEditException: category name cannot be empty.");
        }
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
