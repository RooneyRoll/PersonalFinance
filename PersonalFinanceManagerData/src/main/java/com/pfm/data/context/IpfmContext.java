/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.context;

import com.pfm.data.sets.IUserRoleSet;
import com.pfm.data.sets.IUserSet;
import com.pfm.data.sets.IPaymentCategorySet;
import com.pfm.data.sets.IPaymentSet;
import com.pfm.data.sets.IPaymentTypeSet;
import com.pfm.data.sets.ICategoryBudgetSet;

/**
 *
 * @author Misho
 */
public interface IpfmContext {

    public IUserSet getUserSet();

    public IUserRoleSet getUserRoleSet();

    public IPaymentCategorySet getPaymentCategorySet();

    public IPaymentTypeSet getPaymentTypeSet();

    public IPaymentSet getPaymentSet();

    public ICategoryBudgetSet getCategoryDetailSet();
}
