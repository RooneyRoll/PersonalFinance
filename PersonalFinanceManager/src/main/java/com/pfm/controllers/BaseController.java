/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 *
 * @author mihail
 */

public class BaseController  {
    private final IpfmContext pfmContext;
    private final Authentication auth;

    public BaseController() {
        this.pfmContext = new pfmContext();
        this.auth = SecurityContextHolder.getContext().getAuthentication();
    }

    public IpfmContext getPfmContext() {
        return pfmContext;
    }  

    public Authentication getAuth() {
        return auth;
    }
}
