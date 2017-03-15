/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets.base;

import org.hibernate.Session;


/**
 *
 * @author Misho
 */
public class BaseSet {
    private Session session;

    public BaseSet(Session session){
        this.session = session; 
    }
    
    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }
}
