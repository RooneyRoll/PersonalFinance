/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.paymentType;

import com.pfm.exceptions.ValidationException;

/**
 *
 * @author Misho
 */
public class PaymentTypeEditModel {
    public String typeName;
    public String typeDescription;
    public String typeActive;

    public String getTypeName() throws ValidationException {
        if("".equals(typeName))
            throw new ValidationException("PaymentTypeAddException: category name cannot be empty.");
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public void setTypeDescription(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeActive() {
        return typeActive;
    }

    public void setTypeActive(String typeActive) {
        this.typeActive = typeActive;
    }
    
}
