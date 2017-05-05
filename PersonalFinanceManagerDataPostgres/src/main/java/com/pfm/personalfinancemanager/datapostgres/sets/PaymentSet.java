/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentData;
import com.pfm.data.entities.Payment;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.sets.IPaymentSet;
import com.pfm.personalfinancemanager.datapostgres.entities.Payments;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class PaymentSet extends BaseSet<Payments, Payment, PaymentData> implements IPaymentSet  {

    public PaymentSet(SessionFactory factory) {
        super(factory);
    }
    
    @Override
    protected Payment convertEntityToDto(Payments Entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<Payment> convertEntititiesToDtoArray(List<Payments> EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected Payments convertDtoDataToEntity(PaymentData DtoData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Payment> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Payment GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable Add(PaymentData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(Serializable id, PaymentData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
