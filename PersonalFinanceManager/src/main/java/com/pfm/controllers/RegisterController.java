/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.UserData;
import com.pfm.data.data.UserRoleData;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.exceptions.ValidationException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author Misho
 */
@Controller
public class RegisterController {

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            /* The user is logged in :) */
            return new ModelAndView("redirect:/");
        }
        return new ModelAndView("register");
    }

    @RequestMapping(value = "/regstat", method = RequestMethod.GET)
    public String showCustomer(Model model) {
        boolean success = model.asMap().get("success") != null;
        if (success) {
            return "registerResult";
        } else {
            return "redirect:/login";
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(ModelMap map, HttpServletRequest request,
            HttpServletResponse response, final RedirectAttributes redirectAttributes) throws UserRegisterException {
        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String firstname = request.getParameter("firstname");
            String middlename = request.getParameter("middlename");
            String lastname = request.getParameter("lastname");
            String email = request.getParameter("email");
            if (email != "" && lastname != "" && middlename != "" && firstname != "" && password != "" && username != "") {
                UserData userObject = new UserData();
                userObject.setEmail(email);
                userObject.setEnabled(true);
                userObject.setFirstName(firstname);
                userObject.setLastName(lastname);
                userObject.setMiddleName(middlename);
                userObject.setPassword(password);
                userObject.setUserName(username);
                UserRoleData userRoleObject = new UserRoleData();
                userRoleObject.setUserName(username);
                userRoleObject.setUserRole("ROLE_USER");
                IpfmContext context = pfmContext.getInstance();
                Serializable id = context.getUserSet()
                        .Add(userObject);
                context.getUserRoleSet()
                        .Add(userRoleObject);
                redirectAttributes.addFlashAttribute("success", true);
                redirectAttributes.addFlashAttribute("messageTitle", "Поздравления " + firstname + "!");
                redirectAttributes.addFlashAttribute("messageText", "Вие се регистрирахте успешно. Можете да влезете във вашия профил.");
                return "redirect:/regstat";
            } else {
                throw new ValidationException("Register error: required fields not filled.");
            }
        } catch (ValidationException e) {
            map.put("errorMessage", "Моля въведете всички задължителни полета.");
            return "register";
        }
    }
}
