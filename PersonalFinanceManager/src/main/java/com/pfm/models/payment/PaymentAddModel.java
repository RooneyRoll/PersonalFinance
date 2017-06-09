/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.payment;

import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mihail
 */
public class PaymentAddModel {

    public double paymentAmount;
    public String paymentDescription;
    public UUID paymentCategory;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    public Date paymentDate;

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

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
