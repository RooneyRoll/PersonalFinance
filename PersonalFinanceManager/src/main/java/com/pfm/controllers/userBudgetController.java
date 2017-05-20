/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.User;
import com.pfm.data.entities.UserBudget;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misho
 */
@Controller
public class userBudgetController {

    @RequestMapping(value = "/budgets", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        ModelAndView view = new ModelAndView("/budgets");
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        UserBudget budget = context.getUserBudgetSet()
                .getCurrentlyActiveBudgetForUser(user.getId());
        boolean hasBudget = false;
        if(budget != null){
            hasBudget = true;
        }
        view.addObject("hasBudget", hasBudget);
        return view;
    }
}
