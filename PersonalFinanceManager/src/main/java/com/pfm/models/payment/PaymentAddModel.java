/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.payment;

import com.pfm.exceptions.ValidationException;
import java.util.UUID;

/**
 *
 * @author mihail
 */
public class PaymentAddModel {
    public double paymentAmount;
    public String paymentDescription;
    public UUID paymentCategory;

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public void setPaymentCategory(UUID paymentCategory) {
        this.paymentCategory = paymentCategory;
    }
}
