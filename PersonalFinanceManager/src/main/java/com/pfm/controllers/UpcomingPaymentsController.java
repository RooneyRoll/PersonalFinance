/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class UpcomingPaymentsController extends BaseController {

    @RequestMapping(value = "/getUpcoming", method = RequestMethod.GET)
    public ModelAndView getUpcoming(
            ModelMap map,
            HttpServletRequest request,
            HttpServletResponse response) {
        ModelAndView view = new ModelAndView("payments-upcoming");
        return view;
    }
}
