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
public class RecuringOverviewByPeriodModel {
    
    private String paymentName;
    private Double paymentSinglePeriodAmount;
    private String paymentDescription;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentStartDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date paymentFinishDate;
    private UUID paymentCategory;
    private Integer paymentRecuringType;
    private Integer missPerPeriods;

    public String getPaymentName() {
        return paymentName;
    }

    public Double getPaymentSinglePeriodAmount() {
        return paymentSinglePeriodAmount;
    }

    public String getPaymentDescription() {
        return paymentDescription;
    }

    public Date getPaymentStartDate() {
        return paymentStartDate;
    }

    public Date getPaymentFinishDate() {
        return paymentFinishDate;
    }

    public UUID getPaymentCategory() {
        return paymentCategory;
    }

    public Integer getPaymentRecuringType() {
        return paymentRecuringType;
    }

    public Integer getMissPerPeriods() {
        return missPerPeriods;
    }

    
}
