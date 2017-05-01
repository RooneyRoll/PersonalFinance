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
import java.io.Serializable;

/**
 *
 * @author Misho
 */
public interface IPaymentSet extends IViewable<Payment>,IManagable<PaymentData,Serializable>  {
    
}
