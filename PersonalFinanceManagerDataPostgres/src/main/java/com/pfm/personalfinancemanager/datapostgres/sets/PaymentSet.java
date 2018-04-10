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
import com.pfm.personalfinancemanager.datapostgres.entities.Payments;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
        paymentObject.setConfirmed(Entity.getPConfirmed());
        if (Entity.getPCoveredRecurringPeriods() != null && Entity.getPRecurringBudgetPayment() != null) {
            paymentObject.setCoveredRecurringPeriods(Entity.getPCoveredRecurringPeriods());
            paymentObject.setBudgetRecurringPayment(Entity.getPRecurringBudgetPayment().getRbpId());
        }else{
            paymentObject.setCoveredRecurringPeriods(null);
            paymentObject.setBudgetRecurringPayment(null);
        }
        return paymentObject;
    }

    @Override
    protected List<Payment> convertEntititiesToDtoArray(List<Payments> EntityArray) {
        List<Payment> PaymentList = new ArrayList<>();
        for (Payments next : EntityArray) {
            Payment paymentObject = convertEntityToDto(next);
            PaymentList.add(paymentObject);
        }
        return PaymentList;
    }

    @Override
    protected Payments convertDtoDataToEntity(PaymentData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From PaymentCategories where pcatId = :catId");
            q.setParameter("catId", DtoData.getCategory());
            List<PaymentCategories> categoriesList = q.list();
            Payments payment = new Payments();
            payment.setPActive(DtoData.isActive());
            payment.setPAmount(DtoData.getAmount());
            payment.setPCategory(categoriesList.get(0));
            payment.setPDate(DtoData.getDate());
            payment.setPDescription(DtoData.getDescription());
            payment.setPConfirmed(DtoData.isConfirmed());
            return payment;
        }
    }

    @Override
    public List<Payment> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Payment GetById(UUID id) {
        List<Payment> paymentTypes;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From Payments WHERE pId = :id");
            q.setParameter("id", id);
            List<Payments> resultList = q.list();
            paymentTypes = convertEntititiesToDtoArray(resultList);
        }
        return paymentTypes.get(0);
    }

    @Override
    public UUID Add(PaymentData data) throws BasicException {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Payments PaymentTypeEntity = convertDtoDataToEntity(data);
            Serializable id = session.save(PaymentTypeEntity);
            session.getTransaction().commit();
            session.close();
            return UUID.fromString(id.toString());
        }
    }

    @Override
    public void Edit(UUID id, PaymentData data) throws BasicException {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Payments paymentEntity = convertDtoDataToEntity(data);
            paymentEntity.setPId(id);
            session.update(paymentEntity);
            session.getTransaction().commit();
            session.close();
        }
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID AddOrUpdate(PaymentData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Payment> getAllActivePaymentsByPaymentCategory(UUID paymentCategoryId) {
        List<Payment> payments;
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From Payments p where p.pActive = :isActive and p.pCategory.pcatId = :category")
                    .setParameter("isActive", true)
                    .setParameter("category", paymentCategoryId);
            List<Payments> resultList = q.list();
            payments = convertEntititiesToDtoArray(resultList);
        }
        return payments;
    }

    @Override
    public List<Payment> getAllActiveAndConfirmedPaymentsByPaymentCategoryAndMonth(UUID paymentCategoryId, Date date) {
        List<Payment> payments;
        try (Session session = this.getSessionFactory().openSession()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            Query q = session.createQuery("From Payments p where p.pActive = :isActive and pConfirmed = true and p.pCategory.pcatId = :category and MONTH(p.pDate) = :month and YEAR(p.pDate) = :year order by p.pDate asc")
                    .setParameter("isActive", true)
                    .setParameter("category", paymentCategoryId)
                    .setParameter("month", month)
                    .setParameter("year", year);
            List<Payments> resultList = q.list();
            payments = convertEntititiesToDtoArray(resultList);
        }
        return payments;
    }

    @Override
    public List<Payment> getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndMonth(UUID userId,int paymentType, Date date) {
        List<Payment> payments;
        try (Session session = this.getSessionFactory().openSession()) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH) + 1;
            Query q = session.createQuery("From Payments"
                    + " where pCategory.pcatType.ptypeId = :paymentType and pActive = :isActive and pConfirmed = true and MONTH(pDate) = :month "
                    + "and YEAR(pDate) = :year "
                    + "and pCategory.pcatUser.userUserid = :userId order by pDate asc")
                    .setParameter("isActive", true)
                    .setParameter("paymentType", paymentType)
                    .setParameter("userId",userId)
                    .setParameter("month", month)
                    .setParameter("year", year);
            List<Payments> resultList = q.list();
            payments = convertEntititiesToDtoArray(resultList);
        }
        return payments;
    }
    
    @Override
    public List<Payment> getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndInterval(UUID userId,int paymentType, Date start,Date end) {
        List<Payment> payments;
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From Payments"
                    + " where pCategory.pcatType.ptypeId = :paymentType "
                    + "and pDate BETWEEN :start and :stop "
                    + "and pActive = :isActive and pConfirmed = true "
                    + "and pCategory.pcatUser.userUserid = :userId "
                    + "order by pDate desc")
                    .setParameter("isActive", true)
                    .setParameter("paymentType", paymentType)
                    .setParameter("userId",userId)
                    .setParameter("start", start)
                    .setParameter("stop", end);
            List<Payments> resultList = q.list();
            payments = convertEntititiesToDtoArray(resultList);
        }
        return payments;
    }
}
