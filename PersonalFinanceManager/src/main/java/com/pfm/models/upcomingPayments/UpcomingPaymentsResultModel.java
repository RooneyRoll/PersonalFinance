/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.upcomingPayments;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mihail
 */
public class UpcomingPaymentsResultModel {

    private final Long dateRepresent;
    private final List<UpcomingPaymentResultModel> payments = new ArrayList<>();

    public UpcomingPaymentsResultModel(Long dateRepresent) {
        this.dateRepresent = dateRepresent;
    }

    public Long getDateRepresent() {
        return dateRepresent;
    }

    public List<UpcomingPaymentResultModel> getPayments() {
        return payments;
    }
    
    public void addPayment(UpcomingPaymentResultModel payment){
        this.payments.add(payment);
    }
}
