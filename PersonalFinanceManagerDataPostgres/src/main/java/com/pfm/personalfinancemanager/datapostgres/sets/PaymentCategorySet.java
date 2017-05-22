/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryEditException;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.personalfinancemanager.datapostgres.entities.CategoryBudgets;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
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
public class PaymentCategorySet extends BaseSet<PaymentCategories, PaymentCategory, PaymentCategoryData> implements IPaymentCategorySet {

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
        category.setUserId(Entity.getPcatUser().getUserUserid());
        category.setType(Entity.getPcatType().getPtypeId());
        return category;
    }

    @Override
    protected List<PaymentCategory> convertEntititiesToDtoArray(List<PaymentCategories> EntityArray) {
        List<PaymentCategory> paymentCategoryList = new ArrayList<PaymentCategory>();
        for (PaymentCategories next : EntityArray) {
            paymentCategoryList.add(this.convertEntityToDto(next));
        }
        return paymentCategoryList;
    }

    @Override
    protected PaymentCategories convertDtoDataToEntity(PaymentCategoryData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From Users where userUserid = :userId");
            q.setParameter("userId", DtoData.getUserId());
            List<Users> resultList = q.list();
            Query q1 = session.createQuery("From PaymentTypes where ptypeId = :id");
            q1.setParameter("id", DtoData.getType());
            List<PaymentTypes> typesList = q1.list();
            PaymentCategories paymentEntity = new PaymentCategories();
            paymentEntity.setPcatActive(DtoData.isActive());
            paymentEntity.setPcatDescription(DtoData.getDescription());
            paymentEntity.setPcatName(DtoData.getName());
            paymentEntity.setPcatUser(resultList.get(0));
            paymentEntity.setPcatType(typesList.get(0));
            return paymentEntity;
        }
    }

    @Override
    public UUID Add(PaymentCategoryData data) throws PaymentCategoryAddException {
        try (Session session = this.getSessionFactory().openSession()) {
            if (this.categoryExistsForAdd(data.getName(), data.getUserId(), session)) {
                throw new PaymentCategoryAddException("Payment category with name \"" + data.getName() + "\" already exists.");
            }
            session.beginTransaction();
            PaymentCategories paymentCategoryEntity = convertDtoDataToEntity(data);
            Serializable id = session.save(paymentCategoryEntity);
            session.getTransaction().commit();
            session.close();

            return UUID.fromString(id.toString());
        }
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(UUID id, PaymentCategoryData data) throws PaymentCategoryEditException {
        Session session = this.getSessionFactory().openSession();
        try {
            if (this.categoryExistsForEdit(data.getName(), data.getUserId(), id, session)) {
                throw new PaymentCategoryEditException("Payment category with name \"" + data.getName() + "\" already exists.");
            }
            session.beginTransaction();
            PaymentCategories paymentCategoryEntity = convertDtoDataToEntity(data);
            paymentCategoryEntity.setPcatId(id);
            session.update(paymentCategoryEntity);
            session.getTransaction().commit();
            session.close();
        } finally {
            session.close();
        }
    }

    @Override
    public List<PaymentCategory> GetAll() {
        List<PaymentCategory> userObjects;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From PaymentCategories");
            List<PaymentCategories> resultList = q.list();
            userObjects = convertEntititiesToDtoArray(resultList);
        }
        return userObjects;
    }

    @Override
    public PaymentCategory GetById(UUID id) {
        List<PaymentCategory> paymentCategoryObjects;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From PaymentCategories WHERE pcatId = :id");
            q.setParameter("id", id);
            List<PaymentCategories> resultList = q.list();
            paymentCategoryObjects = convertEntititiesToDtoArray(resultList);
        }
        return paymentCategoryObjects.get(0);
    }

    @Override
    public List<PaymentCategory> GetAllActiveCategoriesForUser(UUID userId) {
        List<PaymentCategory> categoriesResult;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From PaymentCategories pc where pc.pcatUser.userUserid = :userId and pc.pcatActive = :pcatActive", PaymentCategories.class)
                    .setParameter("userId", userId)
                    .setParameter("pcatActive", true);
            List<PaymentCategories> resultList = q.list();
            categoriesResult = convertEntititiesToDtoArray(resultList);
        }
        return categoriesResult;
    }

    private boolean categoryExistsForEdit(String categoryName, UUID userId, UUID categoryId, Session session) {
        Query q = session.createQuery("From PaymentCategories pc where pc.pcatName = :categoryName and pc.pcatUser.userUserid = :userId and pc.pcatId!=:categoryId", PaymentCategories.class)
                .setParameter("categoryName", categoryName)
                .setParameter("userId", userId)
                .setParameter("categoryId", categoryId);

        boolean exists = false;
        if (q.getResultList().size() > 0) {
            exists = true;
        }
       
        return exists;
    }

    private boolean categoryExistsForAdd(String categoryName, UUID userId, Session session) {
        Query q = session.createQuery("From PaymentCategories pc where pc.pcatName = :categoryName and pc.pcatUser.userUserid = :userId", PaymentCategories.class)
                .setParameter("categoryName", categoryName)
                .setParameter("userId", userId);

        boolean exists = false;
        if (q.getResultList().size() > 0) {
            exists = true;
        }
       
        return exists;
    }

    public List<PaymentCategory> getActiveCategoriesByUserIdAndActive(UUID userId, boolean isActive) {

        List<PaymentCategory> paymentCategoryObjects;
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From PaymentCategories pc where pc.pcatUser.userUserid = :userId and pc.pcatActive = :isActive", PaymentCategories.class)
                    .setParameter("userId", userId)
                    .setParameter("isActive", isActive);
            List<PaymentCategories> resultList = q.list();

            paymentCategoryObjects = convertEntititiesToDtoArray(resultList);

        }
        return paymentCategoryObjects;
    }

    @Override
    public List<PaymentCategory> getActiveCategoriesByUserIdAndActiveAndWithNoDetailsAdded(UUID userId, boolean isActive) {
        List<PaymentCategory> paymentCategoryObjects = this.GetAllActiveCategoriesForUser(userId);
        List<PaymentCategory> categoriesWithoutDetails = new ArrayList<>();
        try (Session session = this.getSessionFactory().openSession()) {
            for (PaymentCategory category : paymentCategoryObjects) {
                Query q = session.createQuery("From CategoryBudgets cd where cd.id = :categoryId", CategoryBudgets.class)
                        .setParameter("categoryId", category.getId());
                List<PaymentCategories> resultList = q.list();
                if (resultList.isEmpty()) {
                    categoriesWithoutDetails.add(category);
                }

            }
        }
        return categoriesWithoutDetails;
    }
}
