/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentTypeData;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.sets.IPaymentTypeSet;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentTypes;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class PaymentTypeSet extends BaseSet<PaymentTypes, PaymentType, PaymentTypeData> implements IPaymentTypeSet  {

    public PaymentTypeSet(SessionFactory factory) {
        super(factory);
    }
    
    @Override
    protected PaymentType convertEntityToDto(PaymentTypes Entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<PaymentType> convertEntititiesToDtoArray(List<PaymentTypes> EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected PaymentTypes convertDtoDataToEntity(PaymentTypeData DtoData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaymentType> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaymentType GetById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable Add(PaymentTypeData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(int id, PaymentTypeData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
