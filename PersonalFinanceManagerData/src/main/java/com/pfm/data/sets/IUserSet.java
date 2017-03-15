/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets;

import com.pfm.data.sets.base.IManagable;
import com.pfm.data.sets.base.IViewable;

/**
 *
 * @author Misho
 * @param <EntityType>
 * @param <DTOtype>
 */
public interface IUserSet<EntityType,DTOtype,DataType> extends IViewable<EntityType,DTOtype>,IManagable<DataType,EntityType>{
    
}
