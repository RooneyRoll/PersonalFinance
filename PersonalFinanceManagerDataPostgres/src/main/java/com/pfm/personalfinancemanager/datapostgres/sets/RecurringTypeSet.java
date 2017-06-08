/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.RecurringTypeData;
import com.pfm.data.entities.RecurringType;
import com.pfm.data.sets.IRecurringTypeSet;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringTypes;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.util.List;
import org.hibernate.SessionFactory;

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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected List<RecurringType> convertEntititiesToDtoArray(List<RecurringTypes> EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected RecurringTypes convertDtoDataToEntity(RecurringTypeData DtoData) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<RecurringType> GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public RecurringType GetById(Integer id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
