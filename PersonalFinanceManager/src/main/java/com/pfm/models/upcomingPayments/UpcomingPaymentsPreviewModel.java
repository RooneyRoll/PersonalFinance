/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.models.upcomingPayments;

import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

/**
 *
 * @author mihail
 */
public class UpcomingPaymentsPreviewModel {
    
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final Date startDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private final Date endDate;

    public UpcomingPaymentsPreviewModel(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
    
    
}
