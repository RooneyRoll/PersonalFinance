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
    private final List<UpcomingPaymentCategoryResultModel> categories = new ArrayList<>();

    public UpcomingPaymentsResultModel(Long dateRepresent) {
        this.dateRepresent = dateRepresent;
    }

    public Long getDateRepresent() {
        return dateRepresent;
    }

    public List<UpcomingPaymentCategoryResultModel> getCategories() {
        return categories;
    }

    public void addCategory(UpcomingPaymentCategoryResultModel payment) {
        this.categories.add(payment);
    }

}
