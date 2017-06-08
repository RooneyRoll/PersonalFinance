/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.RecurringBudgetPaymentData;
import com.pfm.data.entities.RecurringBudgetPayment;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.sets.IRecurringBudgetPaymentSet;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringBudgetPayments;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.util.List;
import java.util.UUID;
import org.hibernate.SessionFactory;

/**
 *
 * @author mihail
 */
public class RecurringBudgetPaymentSet extends BaseSet<RecurringBudgetPayments, RecurringBudgetPayment, RecurringBudgetPaymentData> implements IRecurringBudgetPaymentSet {

    public RecurringBudgetPaymentSet(SessionFactory factory) {
        super(factory);
    }
    
    @Override
    protected RecurringBudgetPayment convertEntityToDto(RecurringBudgetPayments Entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<RecurringBudgetPayment> convertEntititiesToDtoArray(List<RecurringBudgetPayments> EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected RecurringBudgetPayments convertDtoDataToEntity(RecurringBudgetPaymentData DtoData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RecurringBudgetPayment> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecurringBudgetPayment GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID Add(RecurringBudgetPaymentData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(UUID id, RecurringBudgetPaymentData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID AddOrUpdate(RecurringBudgetPaymentData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
