/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;
import com.pfm.datagrid.DataGridBuilder;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Misho
 */
@Controller
public class DefaultController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletResponse response, HttpServletRequest request) throws ClassNotFoundException {
        Map<String,String> fields = new HashMap<String,String>();
        fields.put("userEmail","string");
        fields.put("userFirstname","string");
        fields.put("userLastname","string");
        fields.put("userMiddlename","string");
        fields.put("userUsername","date");
        DataGridBuilder grid = new DataGridBuilder(Users.class,fields);
        String gridHtml = grid.buildHtmlForGrid();
        map.put("grid", gridHtml);
        return "home";
    }

}
