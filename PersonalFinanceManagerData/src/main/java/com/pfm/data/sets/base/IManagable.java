/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets.base;

import com.pfm.data.exceptions.BasicException;

/**
 *
 * @author Misho
 */
public interface IManagable<DataType,T> {
    public T Add(DataType data) throws BasicException;
    public void Edit(int id, DataType data);
    public void Delete(int id);
}
