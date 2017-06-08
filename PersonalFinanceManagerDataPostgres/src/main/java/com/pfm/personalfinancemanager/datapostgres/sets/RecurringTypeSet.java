/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.RecurringTypeData;
import com.pfm.data.entities.Payment;
import com.pfm.data.entities.RecurringType;
import com.pfm.data.sets.IRecurringTypeSet;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringTypes;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 *
 * @author Misho
 */
public class RecurringTypeSet extends BaseSet<RecurringTypes, RecurringType, RecurringTypeData> implements IRecurringTypeSet {

    public RecurringTypeSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected RecurringType convertEntityToDto(RecurringTypes Entity) {
        RecurringType recurringTypeObject = new RecurringType();
        recurringTypeObject.setName(Entity.getRtName());
        recurringTypeObject.setId(Entity.getRtId());
        return recurringTypeObject;
    }

    @Override
    protected List<RecurringType> convertEntititiesToDtoArray(List<RecurringTypes> EntityArray) {
        List<RecurringType> recTypeList = new ArrayList<>();
        for (RecurringTypes next : EntityArray) {
            RecurringType recType = convertEntityToDto(next);
            recTypeList.add(recType);
        }
        return recTypeList;
    }

    @Override
    protected RecurringTypes convertDtoDataToEntity(RecurringTypeData DtoData) {
        RecurringTypes payment = new RecurringTypes();
        payment.setRtName(DtoData.getName());
        return payment;
    }

    @Override
    public List<RecurringType> GetAll() {
        List<RecurringType> recTypes;
        try (Session session = this.getSessionFactory().openSession()) {
            Query q = session.createQuery("From RecurringTypes rt");
            List<RecurringTypes> resultList = q.list();
            recTypes = convertEntititiesToDtoArray(resultList);
        }
        return recTypes;
    }

    @Override
    public RecurringType GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
