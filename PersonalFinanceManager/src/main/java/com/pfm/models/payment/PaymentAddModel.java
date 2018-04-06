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

    private double paymentAmount;
    private String paymentDescription;
    private UUID paymentCategory;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentDate;

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public Date getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public void setPaymentDescription(String paymentDescription) {
        this.paymentDescription = paymentDescription;
    }

    public void setPaymentCategory(UUID paymentCategory) {
        this.paymentCategory = paymentCategory;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    
}
