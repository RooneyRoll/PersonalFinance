/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.UserBudgetData;
import com.pfm.data.entities.UserBudget;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.sets.IUserBudgetSet;
import com.pfm.personalfinancemanager.datapostgres.entities.UserBudgets;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.util.ArrayList;
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
public class UserBudgetSet extends BaseSet<UserBudgets, UserBudget, UserBudgetData> implements IUserBudgetSet {

    public UserBudgetSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected UserBudget convertEntityToDto(UserBudgets Entity) {
        UserBudget budget = new UserBudget();
        budget.setFromDate(Entity.getUbFrom());
        budget.setToDate(Entity.getUbTo());
        budget.setUser(Entity.getUbUser().getUserUserid());
        budget.setId(Entity.getUbId());
        return budget;
    }

    @Override
    protected List<UserBudget> convertEntititiesToDtoArray(List<UserBudgets> EntityArray) {
        List<UserBudget> budgets = new ArrayList<>();
        EntityArray.forEach((budget) -> {
            budgets.add(convertEntityToDto(budget));
        });
        return budgets;
    }

    @Override
    protected UserBudgets convertDtoDataToEntity(UserBudgetData DtoData) {
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From Users where userUserid = :userId");
            q.setParameter("userId", DtoData.getUser());
            List<Users> resultList = q.list();
            UserBudgets entity = new UserBudgets();
            entity.setUbFrom(DtoData.getFromDate());
            entity.setUbTo(DtoData.getToDate());
            entity.setUbUser(resultList.get(0));
            return entity;
        }
    }

    @Override
    public List<UserBudget> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserBudget GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UUID Add(UserBudgetData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(UUID id, UserBudgetData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UserBudget getCurrentlyActiveBudgetForUser(UUID userId) {
        Date now = new Date();
        UserBudget budget;
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From UserBudgets b where b.ubUser = :user and b.ubFrom < :from and b.ubTo > :to")
                    .setParameter("user", userId)
                    .setParameter("from", now)
                    .setParameter("to", now);
            List<UserBudgets> resultList = q.list();
            return convertEntityToDto(resultList.get(0));
        }
    }
}
