/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.payment;

import java.util.Date;
import java.util.Map;

/**
 *
 * @author Misho
 */
public class PaymentStatisticsResultObject {

    public String paymentType;
    public Map<Long, Double> amounts;
    public int paymentTypeId;

    public PaymentStatisticsResultObject() {

    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public Map<Long, Double> getAmounts() {
        return amounts;
    }

    public void setAmounts(Map<Long, Double> amounts) {
        this.amounts = amounts;
    }

    public int getPaymentTypeId() {
        return paymentTypeId;
    }

    public void setPaymentTypeId(int paymentTypeId) {
        this.paymentTypeId = paymentTypeId;
    }
}
