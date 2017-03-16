/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;
import com.pfm.data.context.IpfmContext;
import com.pfm.utils.DatabaseConstructor;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.util.CookieGenerator;

/**
 *
 * @author Misho
 */
@Controller
public class DefaultController {
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(ModelMap map,HttpServletResponse response,HttpServletRequest request) {
        map.put("msg", "Hello Spring 4 Web MVC!");
        return "home";
    }
   
}