/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;

/**
 *
 * @author mihail
 */

public class BaseController  {
    private final IpfmContext pfmContext;

    public BaseController() {
        this.pfmContext = new pfmContext();
    }

    public IpfmContext getPfmContext() {
        return pfmContext;
    }  
}
