/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.models.upcomingPayments.UpcomingPaymentsPreviewModel;
import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author mihail
 */
@RestController
public class UpcomingPaymentsRestController extends BaseController{
    
    @RequestMapping(value = "/getUpcomingPayments", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String payments(HttpServletRequest request, @RequestBody UpcomingPaymentsPreviewModel data) {
        
        
        return "";
    }
}
