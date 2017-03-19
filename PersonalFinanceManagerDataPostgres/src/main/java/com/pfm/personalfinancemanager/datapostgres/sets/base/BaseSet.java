/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets.base;

import java.util.List;
import org.hibernate.SessionFactory;


/**
 *
 * @author Misho
 */
public abstract class BaseSet<T,T1,T3> {
    protected SessionFactory sessionFactory;
    
    public BaseSet(SessionFactory session){
        this.sessionFactory = session; 
    }
    
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory factory) {
        this.sessionFactory = factory;
    }
    
    protected abstract T1 convertEntityToDto(T Entity);
    protected abstract List<T1> convertEntititiesToDtoArray(List<T> EntityArray);
    protected abstract T convertDtoDataToEntity(T3 DtoData);
}
