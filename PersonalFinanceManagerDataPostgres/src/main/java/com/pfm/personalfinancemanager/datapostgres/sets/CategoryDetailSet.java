/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.CategoryDetailData;
import com.pfm.data.entities.CategoryDetail;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.sets.ICategoryDetailSet;
import com.pfm.personalfinancemanager.datapostgres.entities.CategoryDetails;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
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
 * @author Admin
 */
public class CategoryDetailSet extends BaseSet<CategoryDetails, CategoryDetail, CategoryDetailData> implements ICategoryDetailSet {

    public CategoryDetailSet(SessionFactory session) {
        super(session);
    }

    @Override
    protected CategoryDetail convertEntityToDto(CategoryDetails Entity) {
        CategoryDetail detail = new CategoryDetail();
        detail.setAmount(Entity.getAmount());
        detail.setFromDate(Entity.getFromDate());
        detail.setToDate(Entity.getFromDate());
        detail.setId(Entity.getId());
        detail.setCategoryId(Entity.getCategoryId());
        return detail;//To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<CategoryDetail> convertEntititiesToDtoArray(List<CategoryDetails> EntityArray) {
        List<CategoryDetail> detailsList = new ArrayList<>();
        for (CategoryDetails detail : EntityArray) {
            detailsList.add(convertEntityToDto(detail));
        }
        return detailsList;
    }

    @Override
    protected CategoryDetails convertDtoDataToEntity(CategoryDetailData DtoData) {
        CategoryDetails details = new CategoryDetails();
        details.setAmount(DtoData.getAmount());
        details.setFromDate(DtoData.getFromDate());
        details.setToDate(DtoData.getToDate());
        details.setCategoryId(DtoData.getCategoryid());
        return details;
    }

    @Override
    public UUID Add(CategoryDetailData data) {

        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            CategoryDetails categotyDetails = convertDtoDataToEntity(data);
            Serializable id = session.save(categotyDetails);
            return UUID.fromString(id.toString());
        }
    }

    @Override
    public void Edit(UUID id, CategoryDetailData data) {
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            CategoryDetails categotyDetails = convertDtoDataToEntity(data);
//            session.
//            Serializable id = session.save(categotyDetails);
//            return UUID.fromString(id.toString());
            return;
        }
    }

    @Override
    public List<CategoryDetail> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public CategoryDetail GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
