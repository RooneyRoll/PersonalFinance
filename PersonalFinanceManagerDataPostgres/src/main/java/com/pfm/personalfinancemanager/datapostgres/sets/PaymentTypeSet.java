/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentTypeData;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.exceptions.PaymentType.PaymentTypeAddException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeEditException;
import com.pfm.data.sets.IPaymentTypeSet;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentTypes;
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
 * @author Misho
 */
public class PaymentTypeSet extends BaseSet<PaymentTypes, PaymentType, PaymentTypeData> implements IPaymentTypeSet {

    public PaymentTypeSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected PaymentType convertEntityToDto(PaymentTypes Entity) {
        PaymentType paymentObject = new PaymentType();
        paymentObject.setActive(Entity.getPtypeActive());
        paymentObject.setDescription(Entity.getPtypeDescription());
        paymentObject.setId(Entity.getPtypeId());
        paymentObject.setName(Entity.getPtypeName());
        paymentObject.setUserId(Entity.getPtypeUser().getUserUserid());
        return paymentObject;
    }

    @Override
    protected List<PaymentType> convertEntititiesToDtoArray(List<PaymentTypes> EntityArray) {
        List<PaymentType> PaymentTypeList = new ArrayList<PaymentType>();
        for (PaymentTypes next : EntityArray) {
            PaymentType paymentObject = new PaymentType();
            paymentObject.setActive(next.getPtypeActive());
            paymentObject.setDescription(next.getPtypeDescription());
            paymentObject.setId(next.getPtypeId());
            paymentObject.setName(next.getPtypeName());
            paymentObject.setUserId(next.getPtypeUser().getUserUserid());
            PaymentTypeList.add(paymentObject);
        }
        return PaymentTypeList;
    }

    @Override
    protected PaymentTypes convertDtoDataToEntity(PaymentTypeData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From Users where userUserid = :userId");
            q.setParameter("userId", DtoData.getUserId());
            List<Users> resultList = q.list();
            PaymentTypes paymentType = new PaymentTypes();
            paymentType.setPtypeActive(DtoData.isActive());
            paymentType.setPtypeDescription(DtoData.getDescription());
            paymentType.setPtypeName(DtoData.getName());
            paymentType.setPtypeUser(resultList.get(0));
            return paymentType;
        }
    }

    @Override
    public List<PaymentType> GetAll() {
        List<PaymentType> paymentResults;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From PaymentTypes");
            List<PaymentTypes> resultList = q.list();
            paymentResults = convertEntititiesToDtoArray(resultList);
        }
        return paymentResults;
    }

    @Override
    public PaymentType GetById(UUID id) {
        Session session = this.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("From PaymentTypes WHERE ptypeId = :id");
        q.setParameter("id", id);
        List<PaymentTypes> resultList = q.list();
        List<PaymentType> paymentTypes = convertEntititiesToDtoArray(resultList);
        session.close();
        return paymentTypes.get(0);
    }

    @Override
    public UUID Add(PaymentTypeData data) throws PaymentTypeAddException {
        try (Session session = this.getSessionFactory().openSession()) {
            if (this.typeExistsForAdd(data.getName(), data.getUserId(), session)) {
                throw new PaymentTypeAddException("Payment type with name \"" + data.getName() + "\" already exists.");
            }
            session.beginTransaction();
            PaymentTypes PaymentTypeEntity = convertDtoDataToEntity(data);
            Serializable id = session.save(PaymentTypeEntity);
            session.getTransaction().commit();
            session.close();

            return UUID.fromString(id.toString());
        }
    }

    @Override
    public void Edit(UUID id, PaymentTypeData data) throws PaymentTypeEditException {
        Session session = this.getSessionFactory().openSession();
        try {
            if (this.typeExistsForEdit(data.getName(), data.getUserId(), id, session)) {
                throw new PaymentTypeEditException("Payment type with name \"" + data.getName() + "\" already exists.");
            }
            session.beginTransaction();
            PaymentTypes paymentTypeEntity = convertDtoDataToEntity(data);
            paymentTypeEntity.setPtypeId(id);
            session.update(paymentTypeEntity);
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

    @Override
    public List<PaymentType> GetAllActiveTypesForUser(UUID userId) {
        Session session = this.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("From PaymentTypes pt where pt.ptypeActive=:typeActive and pt.ptypeUser.userUserid=:userId")
                .setParameter("typeActive", true)
                .setParameter("userId", userId);
        List<PaymentTypes> resultList = q.list();
        List<PaymentType> paymentResults = convertEntititiesToDtoArray(resultList);
        session.close();
        return paymentResults;
    }

    private boolean typeExistsForAdd(String typeName, UUID userId, Session session) {
        Query q = session.createQuery("From PaymentTypes pt where pt.ptypeName=:typeName and pt.ptypeUser=:userId", PaymentTypes.class)
                .setParameter("typeName", typeName)
                .setParameter("userId", userId);

        boolean exists = false;
        if (q.getResultList().size() > 0) {
            exists = true;
        }
        return exists;
    }

    private boolean typeExistsForEdit(String typeName, UUID userId, UUID typeId, Session session) {
        Query q = session.createQuery("From PaymentTypes pt where pt.ptypeName=:typeName and pt.ptypeUser=:userId and pt.ptypeId!=:typeId", PaymentTypes.class)
                .setParameter("typeName", typeName)
                .setParameter("userId", userId)
                .setParameter("typeId", typeId);

        boolean exists = false;
        if (q.getResultList().size() > 0) {
            exists = true;
        }
        return exists;
    }
}
