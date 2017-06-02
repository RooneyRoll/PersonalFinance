/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.enums;

/**
 *
 * @author mihail
 */
public enum PaymentTypes {
    Income(1), Spendings(2);
    
    private final Integer value;
    
    PaymentTypes(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }  
}
