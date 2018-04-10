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
import com.pfm.personalfinancemanager.datapostgres.sets.CategoryBudgetSet;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentCategorySet;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentSet;
import com.pfm.personalfinancemanager.datapostgres.sets.PaymentTypeSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserRoleSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserSet;
import org.hibernate.SessionFactory;
import com.pfm.data.sets.ICategoryBudgetSet;
import com.pfm.data.sets.IRecurringBudgetPaymentSet;
import com.pfm.data.sets.IRecurringTypeSet;
import com.pfm.data.sets.IUserBudgetSet;
import com.pfm.personalfinancemanager.datapostgres.sets.RecurringBudgetPaymentSet;
import com.pfm.personalfinancemanager.datapostgres.sets.RecurringTypeSet;
import com.pfm.personalfinancemanager.datapostgres.sets.UserBudgetSet;

/**
 *
 * @author Misho
 */
public class pfmContext implements IpfmContext {

    private final SessionFactory manager;
    private final IUserSet UserSet;
    private final IUserRoleSet UserRoleSet;
    private final IPaymentCategorySet PaymentCategorySet;
    private final IPaymentTypeSet PaymentTypeSet;
    private final IPaymentSet PaymentSet;
    private final ICategoryBudgetSet categoryBudgetSet;
    private final IUserBudgetSet userBudgetSet;
    private final IRecurringBudgetPaymentSet recurringBudgetPaymentSet;
    private final IRecurringTypeSet recurringTypeSet;
    //private static pfmContext instance;

    /**
     * public static IpfmContext getInstance() { if (instance == null) {
     * instance = new pfmContext(); } return instance; }
     */
    public pfmContext() {
        this.manager = ConfigurationHelper.getSessionFactory();
        this.UserSet = new UserSet(this.manager);
        this.UserRoleSet = new UserRoleSet(this.manager);
        this.PaymentCategorySet = new PaymentCategorySet(this.manager);
        this.PaymentTypeSet = new PaymentTypeSet(this.manager);
        this.PaymentSet = new PaymentSet(this.manager);
        this.categoryBudgetSet = new CategoryBudgetSet(this.manager);
        this.userBudgetSet = new UserBudgetSet(this.manager);
        this.recurringTypeSet = new RecurringTypeSet(this.manager);
        this.recurringBudgetPaymentSet = new RecurringBudgetPaymentSet(this.manager);
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

    @Override
    public ICategoryBudgetSet getCategoryDetailSet() {
        return this.categoryBudgetSet;
    }

    @Override
    public IUserBudgetSet getUserBudgetSet() {
        return this.userBudgetSet;
    }

    @Override
    public IRecurringTypeSet getRecurringTypeSet() {
        return this.recurringTypeSet;
    }

    @Override
    public IRecurringBudgetPaymentSet getRecurringBudgetPaymentSet() {
        return this.recurringBudgetPaymentSet;
    }
}
