/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.entities.User;
import com.pfm.data.data.UserData;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;

/**
 *
 * @author Misho
 */
public interface IUserSet extends IViewable<User>,IManagable<UserData>{
    @Override
    public void Add(UserData data) throws UserRegisterException;
}
