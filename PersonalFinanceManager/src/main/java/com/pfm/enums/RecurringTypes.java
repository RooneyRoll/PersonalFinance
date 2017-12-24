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
public enum RecurringTypes {
    Daily(1), Weekly(2), Monthly(3), Yearly(4);
    
    private final Integer value;
    
    RecurringTypes(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }  
}
