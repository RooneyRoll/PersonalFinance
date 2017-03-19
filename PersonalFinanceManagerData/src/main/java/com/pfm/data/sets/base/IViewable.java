/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.data.sets.base;

import java.util.List;

/**
 *
 * @author Misho
 * @param <DTOtype>
 */
public interface IViewable<DTOtype> {
    public List<DTOtype> GetAll();
    public DTOtype GetById(int id);
}
