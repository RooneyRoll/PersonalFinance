/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewByAmountModel;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewByPeriodModel;
import com.pfm.models.recurringBudgetPayment.RecuringOverviewResultModel;
import com.pfm.utils.recuringPayments.RecuringPaymentsOverviewManager;
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

    @RequestMapping(value = "/recBySumOverview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String recBySumOverview(HttpServletRequest request, @RequestBody RecuringOverviewByAmountModel data) {
        RecuringOverviewResultModel overviewResponceObject = RecuringPaymentsOverviewManager.
                generatePaymentsOverviewByAmount(data);
        Gson gson = new Gson();
        String response = gson.toJson(overviewResponceObject);
        return response;
    }

    @RequestMapping(value = "/recByPeriodOverview", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String recByPeriodOverview(HttpServletRequest request, @RequestBody RecuringOverviewByPeriodModel data) {
        RecuringOverviewResultModel overviewResponceObject = RecuringPaymentsOverviewManager.
                generatePaymentsOverviewByPeriod(data);
        Gson gson = new Gson();
        String response = gson.toJson(overviewResponceObject);
        return response;
    }
}
