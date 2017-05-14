/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryEditException;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Misho
 */
public interface IPaymentCategorySet extends IViewable<PaymentCategory, UUID>, IManagable<PaymentCategoryData, UUID> {

    @Override
    public UUID Add(PaymentCategoryData data) throws PaymentCategoryAddException;
    
    @Override
    public void Edit(UUID id,PaymentCategoryData data) throws PaymentCategoryEditException;
    
    public List<PaymentCategory> getActiveCategoriesByUserIdAndActiveAndWithNoDetailsAdded(UUID userId, boolean isActive) ; 
}
