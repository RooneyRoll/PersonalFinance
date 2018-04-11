/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.User;
import java.util.List;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
public class UpcomingPaymentsController extends BaseController {

    @RequestMapping(value = "/getUpcoming", method = RequestMethod.GET)
    public ModelAndView getUpcoming(
            ModelMap map,
            HttpServletRequest request,
            HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = this.getPfmContext();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        ModelAndView view = new ModelAndView("payments-upcoming");
        return view;
    }

    private void generateUpcomingPaymentsForUser(UUID userId,IpfmContext context) {
        List<PaymentCategory> userCategories = context.getPaymentCategorySet().GetAllActiveCategoriesForUser(userId);
        
    }
}
