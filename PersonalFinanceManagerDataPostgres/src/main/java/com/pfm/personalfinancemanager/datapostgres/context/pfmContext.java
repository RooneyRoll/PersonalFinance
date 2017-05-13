/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.context;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.data.sets.IPaymentSet;
import com.pfm.data.sets.IPaymentTypeSet;
import com.pfm.data.sets.IUserRoleSet;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.config.ConfigurationHelper;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentCategorySet;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentSet;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentTypeSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserRoleSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserSet;
import org.hibernate.SessionFactory;

/**
 *
 * @author Misho
 */
public class pfmContext implements IpfmContext {

    private SessionFactory manager;
    private IUserSet UserSet;
    private IUserRoleSet UserRoleSet;
    private IPaymentCategorySet PaymentCategorySet;
    private IPaymentTypeSet PaymentTypeSet;
    private IPaymentSet PaymentSet;
    private static pfmContext instance;

    public static IpfmContext getInstance() {
        if (instance == null) {
            instance = new pfmContext();
        }

        return instance;
    }

    private pfmContext() {
        this.manager = ConfigurationHelper.getSessionFactory();
        this.UserSet = new UserSet(this.manager);
        this.UserRoleSet = new UserRoleSet(this.manager);
        this.PaymentCategorySet = new PaymentCategorySet(this.manager);
        this.PaymentTypeSet = new PaymentTypeSet(this.manager);
        this.PaymentSet = new PaymentSet(this.manager);
    }

    @Override
    public IUserSet getUserSet() {
        return this.UserSet;
    }

    @Override
    public IUserRoleSet getUserRoleSet() {
        return this.UserRoleSet;
    }

    @Override
    public IPaymentCategorySet getPaymentCategorySet() {
        return this.PaymentCategorySet;
    }

    @Override
    public IPaymentTypeSet getPaymentTypeSet() {
        return this.PaymentTypeSet;
    }

    @Override
    public IPaymentSet getPaymentSet() {
        return this.PaymentSet;
    }
}
