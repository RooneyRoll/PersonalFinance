/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.UserData;
import com.pfm.data.entities.User;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Misho
 */
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {

        IpfmContext context = new pfmContext();
        System.out.println("----Register");
        UserData userInput = new UserData();
        userInput.setEmail("test@gmail");
        userInput.setEnabled(true);
        userInput.setFirstName("test");
        userInput.setLastName("test1");
        userInput.setMiddleName("test123");
        userInput.setPassword("1234567");
        userInput.setUserName("usernametest");
        context.getUserSet().Add(userInput);
        List<User> users =  context.getUserSet().GetAll();
        System.out.println(users.size());
        map.put("name", "val");
        return "register";
    }
}
