/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class PaymentCategorySet extends BaseSet<PaymentCategories, PaymentCategory, PaymentCategoryData> implements IPaymentCategorySet {
    
    public PaymentCategorySet(SessionFactory factory) {
        super(factory);
    }
    
    @Override
    protected PaymentCategory convertEntityToDto(PaymentCategories Entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<PaymentCategory> convertEntititiesToDtoArray(List<PaymentCategories> EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected PaymentCategories convertDtoDataToEntity(PaymentCategoryData DtoData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaymentCategory> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaymentCategory GetById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable Add(PaymentCategoryData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(int id, PaymentCategoryData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
