/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets.base;

import org.hibernate.SessionFactory;


/**
 *
 * @author Misho
 */
public class BaseSet {
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
}
