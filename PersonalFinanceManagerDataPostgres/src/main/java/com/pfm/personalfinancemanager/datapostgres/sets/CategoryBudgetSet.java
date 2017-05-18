/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.CategoryBudgetData;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.personalfinancemanager.datapostgres.entities.CategoryDetails;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import com.pfm.data.sets.ICategoryBudgetSet;
import com.pfm.personalfinancemanager.datapostgres.entities.CategoryBudgets;

/**
 *
 * @author Admin
 */
public class CategoryBudgetSet extends BaseSet<CategoryBudgets, CategoryBudget, CategoryBudgetData> implements ICategoryBudgetSet {

    public CategoryBudgetSet(SessionFactory session) {
        super(session);
    }

    @Override
    protected CategoryBudget convertEntityToDto(CategoryBudgets Entity) {
        CategoryBudget detail = new CategoryBudget();
        detail.setAmount(Entity.getAmount());
        detail.setFromDate(Entity.getFromDate());
        detail.setToDate(Entity.getFromDate());
        detail.setId(Entity.getId());
        detail.setCategoryId(Entity.getCategoryId());
        return detail;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<CategoryBudget> convertEntititiesToDtoArray(List<CategoryBudgets> EntityArray) {
        List<CategoryBudget> detailsList = new ArrayList<>();
        for (CategoryBudgets detail : EntityArray) {
            detailsList.add(convertEntityToDto(detail));
        }
        return detailsList;
    }

    @Override
    protected CategoryBudgets convertDtoDataToEntity(CategoryBudgetData DtoData) {
        CategoryBudgets details = new CategoryBudgets();
        details.setAmount(DtoData.getAmount());
        details.setFromDate(DtoData.getFromDate());
        details.setToDate(DtoData.getToDate());
        details.setCategoryId(DtoData.getCategoryid());
        return details;
    }

    @Override
    public UUID Add(CategoryBudgetData data) {
        Serializable id = null  ;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();

            CategoryBudgets categotyDetails = convertDtoDataToEntity(data);
          
                System.out.println(categotyDetails);
                id = session.save(categotyDetails);
                session.getTransaction().commit();

            return UUID.fromString(id.toString());
        }
    }

    @Override
    public void Edit(UUID id, CategoryBudgetData data) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            CategoryBudgets categotyDetails = convertDtoDataToEntity(data);
//            session.
//            Serializable id = session.save(categotyDetails);
//            return UUID.fromString(id.toString());
            return;
        }
    }

    @Override
    public List<CategoryBudget> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoryBudget GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
