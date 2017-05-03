/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.exceptions.PaymentCategoryAddException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misho
 */
@Controller
public class PaymentCategoriesController {

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) {

        return new ModelAndView("categories-manage");
    }

    @RequestMapping(value = "/categories/add", method = RequestMethod.GET)
    public ModelAndView addIndex(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {

        return new ModelAndView("categories-add");
    }

    @RequestMapping(value = "/categories/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {
        String name = request.getParameter("category-name");
        String description = request.getParameter("category-description");

        PaymentCategoryData categoryDataObject = new PaymentCategoryData();
        categoryDataObject.setActive(true);
        categoryDataObject.setDescription(name);
        categoryDataObject.setName(description);
        IpfmContext context = pfmContext.getInstance();
        Serializable id = context.getPaymentCategorySet()
                .Add(categoryDataObject);

        return new ModelAndView("categories-add");
    }
}
