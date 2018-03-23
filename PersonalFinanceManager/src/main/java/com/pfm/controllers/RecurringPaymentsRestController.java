/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.models.budgetService.BudgetParamObject;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Misho
 */
@RestController
public class RecurringPaymentsRestController {
    @RequestMapping(value = "/recPreview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String budgetData(HttpServletRequest request, @RequestBody BudgetParamObject params) {
        List<CategoryBudget> categories = new ArrayList<>();
        
        Gson gson = new Gson();
        String json = gson.toJson(categories);
        return json;
    }
}
