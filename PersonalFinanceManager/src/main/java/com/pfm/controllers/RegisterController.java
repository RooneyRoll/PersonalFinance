/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.UserData;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
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

        //map.put("name", "val");
        return "register";
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap map, HttpServletRequest request, HttpServletResponse response) throws UserRegisterException {
        String username = request.getParameter("UserName");
        String password = request.getParameter("Password");
        String firstname = request.getParameter("FirstName");
        String middlename = request.getParameter("MiddleName");
        String lastname = request.getParameter("LastName");
        String email = request.getParameter("Email");
        if (email != "" && lastname != "" && middlename != "" && firstname != "" && password != "" && username != "") {
            UserData userObject = new UserData();
            userObject.setEmail(email);
            userObject.setEnabled(true);
            userObject.setFirstName(firstname);
            userObject.setLastName(lastname);
            userObject.setMiddleName(middlename);
            userObject.setPassword(password);
            userObject.setUserName(username);
            IpfmContext context = new pfmContext();
            context.getUserSet()
                    .Add(userObject);
        }else{
            //throw new 
        }

        map.put("message", "Успешна регистрация на потребител: " + username);
        return "result";
    }
}
