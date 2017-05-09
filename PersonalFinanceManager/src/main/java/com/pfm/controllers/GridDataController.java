/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridDataManager;
import com.pfm.personalfinancemanagergrid.classes.requestObjects.GridParamObject;

/**
 *
 * @author Misho
 */
@RestController
public class GridDataController {

    @RequestMapping(value = "/gridData", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String gridData(HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody GridParamObject params) {
            DataGridDataManager manager = new DataGridDataManager(params);
            String result = manager.getData();
            return result;
    }
}
