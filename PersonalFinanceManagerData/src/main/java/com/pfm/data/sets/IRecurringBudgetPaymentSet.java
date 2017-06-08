/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.RecurringBudgetPaymentData;
import com.pfm.data.entities.RecurringBudgetPayment;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.UUID;

/**
 *
 * @author mihail
 */
public interface IRecurringBudgetPaymentSet extends IViewable<RecurringBudgetPayment, UUID>, IManagable<RecurringBudgetPaymentData, UUID> {
    
}
