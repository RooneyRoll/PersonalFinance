/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.data.entities.User;
import com.pfm.data.entities.UserBudget;
import com.pfm.models.budgetService.BudgetParamObject;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Misho
 */
@RestController
public class userBudgetRestController {

    @RequestMapping(value = "/budget", method = RequestMethod.POST, produces = "application/json;charset=utf-8")

    public String budgetData(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        List<CategoryBudget> categories = new ArrayList<>();
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            DateFormat format = new SimpleDateFormat("MM/yyyy");
            UserBudget budget = context.getUserBudgetSet()
                    .getBudgetByDateAndUserId(user.getId(), format.parse(params.getMonth() + "/" + params.getYear()));
            categories = context.
                    getCategoryDetailSet().
                    GetAllActiveCategoryBudgetsByBudgetId(budget.getId());
        } catch (ParseException ex) {
            System.out.println(ex.getMessage());
        } catch (EntityNotFoundException exp) {
            System.out.println("budget not found");
        }
        Gson gson = new Gson();
        String json = gson.toJson(categories);
            return json;
    }
}
