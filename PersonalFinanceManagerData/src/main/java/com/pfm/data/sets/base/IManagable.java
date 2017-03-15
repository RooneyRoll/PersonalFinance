/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets.base;

/**
 *
 * @author Misho
 */
public interface IManagable<DataType,entityType> {
    public void Add(DataType data);
    public void Edit(int id, DataType data);
    public void Delete(int id);
    public void FillEntityFromDTOData(entityType entity, DataType data);
}
