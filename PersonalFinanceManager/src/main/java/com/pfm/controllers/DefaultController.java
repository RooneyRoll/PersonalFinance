/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.User;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import com.pfm.personalfinancemanagergrid.classes.DataGridBuilder;
import java.util.HashMap;
import java.util.List;
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

    @RequestMapping(value ={ "/","/home"}, method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletResponse response, HttpServletRequest request) throws ClassNotFoundException {
        IpfmContext context = pfmContext.getInstance();
        //Map<String,String> fields = new HashMap<String,String>();;
        //fields.put("userUserid","string");
        //fields.put("userEmail","string");
        //fields.put("userEnabled","string");
        //fields.put("userFirstname","string");
        
        //DataGridBuilder grid = new DataGridBuilder(Users.class,fields);
        //String gridHtml = grid.buildHtmlForGrid();
        //map.put("grid", gridHtml);
        return "home";
    }

}
