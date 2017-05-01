/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.data.UserData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.SessionFactory;

/**
 *
 * @author Admin
 */
public class PaymentCategorySet extends BaseSet<PaymentCategories, PaymentCategory, PaymentCategoryData> implements IPaymentCategorySet {

    public PaymentCategorySet(SessionFactory session) {
        super(session);
    }

    @Override
    protected PaymentCategory convertEntityToDto(PaymentCategories Entity) {
        PaymentCategory category = new PaymentCategory();
        category.setId(Entity.getId());
        category.setName(Entity.getName());
        return category;
    }

    @Override
    protected List<PaymentCategory> convertEntititiesToDtoArray(List<PaymentCategories> EntityArray) {
        List<PaymentCategory> categoryList = new ArrayList<>();
        for (PaymentCategories category : EntityArray) {
            categoryList.add(convertEntityToDto(category));
        }
        return categoryList; //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected PaymentCategories convertDtoDataToEntity(PaymentCategoryData DtoData) {
        PaymentCategories category = new PaymentCategories(DtoData.getName());
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable Add(PaymentCategoryData data) throws UserRegisterException {
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
    public void Edit(int id, PaymentCategoryData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
