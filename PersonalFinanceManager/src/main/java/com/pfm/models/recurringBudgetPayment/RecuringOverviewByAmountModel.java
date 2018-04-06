/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.recurringBudgetPayment;

import java.util.Date;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mihail
 */
public class RecuringOverviewByAmountModel {
    
    private Integer missPerPeriods;
    private Integer periodsCount;
    private String paymentName;
    private String paymentDescription;
    private Double paymentFinalAmount;
    private Double paymentInitialAmount;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentStartDate;
    private UUID paymentCategory;
    private Integer paymentRecuringType;

    public Integer getMissPerPeriods() {
        return missPerPeriods;
    }

    public Integer getPeriodsCount() {
        return periodsCount;
    }

    public String getPaymentName() {
        return paymentName;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public Double getPaymentFinalAmount() {
        return paymentFinalAmount;
    }

    public Double getPaymentInitialAmount() {
        return paymentInitialAmount;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public Integer getPaymentRecuringType() {
        return paymentRecuringType;
    }
    
}
