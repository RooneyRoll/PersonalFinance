/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.PaymentData;
import com.pfm.data.entities.Payment;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Misho
 */
public interface IPaymentSet extends IViewable<Payment, UUID>, IManagable<PaymentData, UUID> {

    public List<Payment> getAllActivePaymentsByPaymentCategory(UUID paymentCategoryId);

    public List<Payment> getAllActiveAndConfirmedPaymentsByPaymentCategoryAndMonth(UUID paymentCategoryId, Date date);

    public List<Payment> getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndMonth(UUID userId,int paymentType, Date date);
}
