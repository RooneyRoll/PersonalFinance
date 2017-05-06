/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.PaymentTypeData;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.exceptions.PaymentType.PaymentTypeAddException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeEditException;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.io.Serializable;
import java.util.UUID;

/**
 *
 * @author Misho
 */
public interface IPaymentTypeSet extends IViewable<PaymentType, UUID>, IManagable<PaymentTypeData, UUID> {

    @Override
    public UUID Add(PaymentTypeData data) throws PaymentTypeAddException;

    @Override
    public void Edit(UUID id, PaymentTypeData data) throws PaymentTypeEditException;
}
