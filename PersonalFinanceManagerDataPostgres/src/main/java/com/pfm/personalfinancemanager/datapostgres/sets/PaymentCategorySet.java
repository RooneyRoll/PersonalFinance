/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.exceptions.PaymentCategoryAddException;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class PaymentCategorySet extends BaseSet<PaymentCategories, PaymentCategory, PaymentCategoryData> implements IPaymentCategorySet   {

    public PaymentCategorySet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected PaymentCategory convertEntityToDto(PaymentCategories Entity) {
        PaymentCategory category = new PaymentCategory();
            category.setActive(Entity.getPcatActive());
            category.setDescription(Entity.getPcatDescription());
            category.setId(Entity.getPcatId());
            category.setName(Entity.getPcatName());
            category.setUserId(Entity.getPcatUser());
            return category;
    }

    @Override
    protected List<PaymentCategory> convertEntititiesToDtoArray(List<PaymentCategories> EntityArray) {
        List<PaymentCategory> paymentCategoryList = new ArrayList<PaymentCategory>();
        for (PaymentCategories next : EntityArray) {
            PaymentCategory category = new PaymentCategory();
            category.setActive(next.getPcatActive());
            category.setDescription(next.getPcatDescription());
            category.setId(next.getPcatId());
            category.setName(next.getPcatName());
            category.setUserId(next.getPcatUser());
            paymentCategoryList.add(category);
        }
        return paymentCategoryList;
    }

    @Override
    protected PaymentCategories convertDtoDataToEntity(PaymentCategoryData DtoData) {
        PaymentCategories paymentEntity = new PaymentCategories();
        paymentEntity.setPcatActive(DtoData.isActive());
        paymentEntity.setPcatDescription(DtoData.getDescription());;
        paymentEntity.setPcatName(DtoData.getName());
        paymentEntity.setPcatUser(DtoData.getUserId());
        return paymentEntity;
    }

    @Override
    public Serializable Add(PaymentCategoryData data) throws PaymentCategoryAddException {
        Session session = this.getSessionFactory().openSession();
        try{
        session.beginTransaction();
        PaymentCategories paymentCategoryEntity = convertDtoDataToEntity(data);
        Serializable id = session.save(paymentCategoryEntity);
        session.getTransaction().commit();
        session.close();
        return id;
        }finally{
            session.close();
        }
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(int id, PaymentCategoryData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<PaymentCategory> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public PaymentCategory GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
