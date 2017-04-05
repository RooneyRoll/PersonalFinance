/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.utils;

import com.pfm.data.context.IpfmContext;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;

/**
 *
 * @author Misho
 */
public class DatabaseConstructor {
    public static IpfmContext getNewContext(){
        return pfmContext.getInstance();
    }
}
