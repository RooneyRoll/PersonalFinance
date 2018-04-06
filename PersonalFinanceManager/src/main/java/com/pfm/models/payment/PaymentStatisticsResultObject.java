/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.payment;

import java.util.Map;

/**
 *
 * @author Misho
 */
public class PaymentStatisticsResultObject {

    private String paymentType;
    private Map<Long, Double> amounts;
    private Integer paymentTypeId;

    public String getPaymentType() {
        return paymentType;
    }

    public Map<Long, Double> getAmounts() {
        return amounts;
    }

    public Integer getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public void setAmounts(Map<Long, Double> amounts) {
        this.amounts = amounts;
    }

    public void setPaymentTypeId(Integer paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
}
