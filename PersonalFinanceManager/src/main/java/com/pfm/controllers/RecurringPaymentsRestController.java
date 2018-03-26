/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.models.recurringBudgetPayment.RecurringBudgetPaymentAddModel;
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
    @RequestMapping(value = "/recPaymentGetOverview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String recPaymentPreview(HttpServletRequest request, @RequestBody RecurringBudgetPaymentAddModel recuringPaymentData) {
        System.out.println(recuringPaymentData.getPaymentName());
        //Gson gson = new Gson();
        //String json = gson.toJson(categories);
        //return json;
        return "";
    }
}
