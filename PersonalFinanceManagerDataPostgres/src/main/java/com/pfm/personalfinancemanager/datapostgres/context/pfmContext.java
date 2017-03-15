/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.context;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.config.ConfigurationHelper;
import com.pfm.personalfinancemanager.datapostgres.sets.UserSet;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class pfmContext implements IpfmContext{
    private Session manager;
    private IUserSet UserSet;
    
    public pfmContext(){
        this.manager = ConfigurationHelper.getSessionFactory().openSession();
        this.UserSet = new UserSet(this.manager);
    }
    
    public IUserSet getUserSet() {
        return this.UserSet;
    }
    
}
