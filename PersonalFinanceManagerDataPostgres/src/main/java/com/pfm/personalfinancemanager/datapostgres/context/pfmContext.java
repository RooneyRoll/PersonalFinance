/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.context;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.sets.IUserRoleSet;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.config.ConfigurationHelper;
import com.pfm.personalfinancemanager.datapostgres.sets.UserRoleSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserSet;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class pfmContext implements IpfmContext{
    private SessionFactory manager;
    private IUserSet UserSet;
    private IUserRoleSet UserRoleSet;
    private static pfmContext instance;

    public static IpfmContext getInstance(){
        if(instance == null){
            instance = new pfmContext();
        }

        return instance;
    }

    private pfmContext(){
        this.manager = ConfigurationHelper.getSessionFactory();
        this.UserSet = new UserSet(this.manager);
        this.UserRoleSet = new UserRoleSet(this.manager);
    }
    
    public IUserSet getUserSet() {
        return this.UserSet;
    }

    @Override
    public IUserRoleSet getUserRoleSet() {
        return this.UserRoleSet;
    }
    
}
