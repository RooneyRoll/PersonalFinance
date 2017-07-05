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
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringBudgetPayments;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringTypes;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

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
        RecurringBudgetPayment paymentObject = new RecurringBudgetPayment();
        paymentObject.setActive(Entity.getRbpActive());
        paymentObject.setAmount(Entity.getRbpAmount());
        paymentObject.setId(Entity.getRbpId());
        paymentObject.setPaymentCategoryId(Entity.getRbpCategory().getPcatId());
        paymentObject.setPeriods(Entity.getRbpPeriods());
        paymentObject.setRecurringType(Entity.getRbpRecType().getRtId());
        paymentObject.setStartDate(Entity.getRbpDateStart());
        paymentObject.setUserId(Entity.getRbpUser().getUserUserid());
        paymentObject.setDescription(Entity.getRbpDescription());
        paymentObject.setTitle(Entity.getRbpTitle());
        paymentObject.setFinished(Entity.getRbvFinished());
        paymentObject.setFinishedDate(Entity.getRbpFinishedDate());
        paymentObject.setMissPerPeriods(Entity.getRbpMissPerPeriods());
        return paymentObject;
    }
    
    @Override
    protected List<RecurringBudgetPayment> convertEntititiesToDtoArray(List<RecurringBudgetPayments> EntityArray) {
        List<RecurringBudgetPayment> recBudgetPaymentList = new ArrayList<>();
        for (RecurringBudgetPayments next : EntityArray) {
            RecurringBudgetPayment recBudgetPayment = convertEntityToDto(next);
            recBudgetPaymentList.add(recBudgetPayment);
        }
        return recBudgetPaymentList;
    }
    
    @Override
    protected RecurringBudgetPayments convertDtoDataToEntity(RecurringBudgetPaymentData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query catsQuery = session.createQuery("From PaymentCategories where pcatId = :catId");
            catsQuery.setParameter("catId", DtoData.getPaymentCategoryId());
            List<PaymentCategories> categoriesList = catsQuery.list();
            Query recTypesQuery = session.createQuery("From RecurringTypes rt where rt.rtId = :id");
            recTypesQuery.setParameter("id", DtoData.getRecurringType());
            List<RecurringTypes> recTypesList = recTypesQuery.list();
            Query userQuery = session.createQuery("From Users where userUserid = :userId");
            userQuery.setParameter("userId", DtoData.getUserId());
            List<Users> userList = userQuery.list();
            RecurringBudgetPayments entity = new RecurringBudgetPayments();
            entity.setRbpActive(DtoData.isActive());
            entity.setRbpAmount(DtoData.getAmount());
            entity.setRbpCategory(categoriesList.get(0));
            entity.setRbpDateStart(DtoData.getStartDate());
            entity.setRbpPeriods(DtoData.getPeriods());
            entity.setRbpRecType(recTypesList.get(0));
            entity.setRbpUser(userList.get(0));
            entity.setRbpDescription(DtoData.getDescription());
            entity.setRbpTitle(DtoData.getTitle());
            entity.setRbvFinished(DtoData.isFinished());
            entity.setRbpFinishedDate(DtoData.getFinishedDate());
            entity.setRbpMissPerPeriods(DtoData.getMissPerPeriods());
            return entity;
        }
    }
    
    @Override
    public List<RecurringBudgetPayment> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    public RecurringBudgetPayment GetById(UUID id) {
        List<RecurringBudgetPayment> paymentTypes;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From RecurringBudgetPayments WHERE rbpId = :id");
            q.setParameter("id", id);
            List<RecurringBudgetPayments> resultList = q.list();
            paymentTypes = convertEntititiesToDtoArray(resultList);
        }
        return paymentTypes.get(0);
    }
    
    @Override
    public UUID Add(RecurringBudgetPaymentData data) throws BasicException {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            RecurringBudgetPayments entity = convertDtoDataToEntity(data);
            Serializable id = session.save(entity);
            session.getTransaction().commit();
            session.close();
            return UUID.fromString(id.toString());
        }
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
