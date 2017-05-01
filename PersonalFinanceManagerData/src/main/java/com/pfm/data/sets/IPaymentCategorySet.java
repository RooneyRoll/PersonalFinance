/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.io.Serializable;

/**
 *
 * @author Admin
 */
public interface IPaymentCategorySet extends IViewable<PaymentCategory>,IManagable<PaymentCategoryData,Serializable>{
    @Override
    public Serializable Add(PaymentCategoryData data) throws UserRegisterException;
}
