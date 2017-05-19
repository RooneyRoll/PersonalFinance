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
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentTypes;
import com.pfm.personalfinancemanager.datapostgres.entities.Payments;
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
 * @author Misho
 */
public class PaymentSet extends BaseSet<Payments, Payment, PaymentData> implements IPaymentSet {

    public PaymentSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected Payment convertEntityToDto(Payments Entity) {
        Payment paymentObject = new Payment();
        paymentObject.setActive(Entity.getPActive());
        paymentObject.setAmount(Entity.getPAmount());
        paymentObject.setCategory(Entity.getPCategory().getPcatId());
        paymentObject.setDate(Entity.getPDate());
        paymentObject.setDescription(Entity.getPDescription());
        paymentObject.setId(Entity.getPId());
        paymentObject.setpType(Entity.getPType().getPtypeId());
        return paymentObject;
    }

    @Override
    protected List<Payment> convertEntititiesToDtoArray(List<Payments> EntityArray) {
        List<Payment> PaymentList = new ArrayList<Payment>();
        for (Payments next : EntityArray) {
            Payment paymentObject = new Payment();
            paymentObject.setActive(next.getPActive());
            paymentObject.setAmount(next.getPAmount());
            paymentObject.setCategory(next.getPCategory().getPcatId());
            paymentObject.setDate(next.getPDate());
            paymentObject.setDescription(next.getPDescription());
            paymentObject.setId(next.getPId());
            paymentObject.setpType(next.getPType().getPtypeId());
            PaymentList.add(paymentObject);
        }
        return PaymentList;
    }

    @Override
    protected Payments convertDtoDataToEntity(PaymentData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From PaymentCategories where pcatId = :catId");
            q.setParameter("catId", DtoData.getCategory());
            List<PaymentCategories> categoriesList = q.list();
            Query q1 = session.createQuery("From PaymentTypes where ptypeId = :typeId");
            q.setParameter("typeId", DtoData.getType());
            List<PaymentTypes> typesList = q1.list();
            Payments payment = new Payments();
            payment.setPActive(true);
            payment.setPAmount(DtoData.getAmount());
            payment.setPCategory(categoriesList.get(0));
            payment.setPDate(DtoData.getDate());
            payment.setPDescription(DtoData.getDescription());
            payment.setPType(typesList.get(0));
            return payment;
        }

    }

    @Override
    public List<Payment> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Payment GetById(UUID id) {
        Session session = this.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("From Payments WHERE pId = :id");
        q.setParameter("id", id);
        List<Payments> resultList = q.list();
        List<Payment> paymentTypes = convertEntititiesToDtoArray(resultList);
        session.close();
        return paymentTypes.get(0);
    }

    @Override
    public UUID Add(PaymentData data) throws BasicException {
        Session session = this.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Payments PaymentTypeEntity = convertDtoDataToEntity(data);
            Serializable id = session.save(PaymentTypeEntity);
            session.getTransaction().commit();
            session.close();
            return UUID.fromString(id.toString());
        } finally {
            session.close();
        }
    }

    @Override
    public void Edit(UUID id, PaymentData data) throws BasicException {
        Session session = this.getSessionFactory().openSession();
        try {
            session.beginTransaction();
            Payments paymentEntity = convertDtoDataToEntity(data);
            paymentEntity.setPId(id);
            session.update(paymentEntity);
            session.getTransaction().commit();
            session.close();
        } finally {
            session.close();
        }
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
