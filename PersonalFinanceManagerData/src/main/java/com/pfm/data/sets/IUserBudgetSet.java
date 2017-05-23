/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.data.UserBudgetData;
import com.pfm.data.entities.UserBudget;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;
import java.util.Date;
import java.util.UUID;

/**
 *
 * @author Misho
 */
public interface IUserBudgetSet extends IViewable<UserBudget,UUID>,IManagable<UserBudgetData,UUID>  {
    public UserBudget getCurrentlyActiveBudgetForUser(UUID userId);
    public UserBudget getBudgetByDateAndUserId(UUID userId, Date date);
}
