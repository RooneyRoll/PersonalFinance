/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.PaymentTypeData;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeAddException;
import com.pfm.data.exceptions.PaymentType.PaymentTypeEditException;
import com.pfm.exceptions.ValidationException;
import com.pfm.models.paymentType.PaymentTypeAddModel;
import com.pfm.models.paymentType.PaymentTypeEditModel;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentTypes;
import com.pfm.personalfinancemanagergrid.classes.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.classes.columnSettingsObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author Misho
 */
@Controller
public class PaymentTypesController {
    @RequestMapping(value = "/types", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {
            List<columnSettingsObject> columnsList = new ArrayList<columnSettingsObject>();
            columnsList.add(new columnSettingsObject("ptypeName", "Име", "string", true));
            columnsList.add(new columnSettingsObject("ptypeActive","Активност","string", true));
            columnsList.add(new columnSettingsObject("ptypeDescription", "Описание", "string", true));
            DataGridBuilder grid = new DataGridBuilder(PaymentTypes.class,columnsList);
            String gridHtml = grid.buildHtmlForGrid();
            ModelAndView view = new ModelAndView("types-manage");
            view.addObject("grid", gridHtml);
        return view;
    }

    @RequestMapping(value = "/types/add", method = RequestMethod.GET)
    public ModelAndView addIndex(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {

        return new ModelAndView("types-add");
    }

    @RequestMapping(value = "/types/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute PaymentTypeAddModel params) throws PaymentTypeAddException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            PaymentTypeData PaymentTypeObject = new PaymentTypeData();
            PaymentTypeObject.setActive(true);
            PaymentTypeObject.setDescription(params.getTypeDescription());
            PaymentTypeObject.setName(params.getTypeName());
            PaymentTypeObject.setUserId(user.getId());
            UUID id = context.getPaymentTypeSet()
                    .Add(PaymentTypeObject);
            String buttonSubmitted = request.getParameter("submit-button");
            ModelAndView view = null;
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/types");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/types/edit/" + id);
                    break;
                case "3":
                    view = new ModelAndView("redirect:/types/add");
                    break;
            }
            return view;
        } catch (ValidationException e) {
            ModelAndView view = new ModelAndView("types-add");
            view.addObject("errorMessage", "Моля въведете всички задължителни полета.");
            return view;
        }
    }

    @RequestMapping(value = "/types/edit/{typeId}", method = RequestMethod.GET)
    public ModelAndView editIndex(ModelMap map, HttpServletRequest request,
            @PathVariable("typeId") UUID typeId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentTypeEditException {
        IpfmContext context = pfmContext.getInstance();
        PaymentType type = context.getPaymentTypeSet().GetById(typeId);
        ModelAndView view = new ModelAndView("types-edit");
        PaymentTypeEditModel model = new PaymentTypeEditModel();
        model.setTypeActive(type.isActive() ? "1" : "2");
        model.setTypeDescription(type.getDescription());
        model.setTypeName(type.getName());
        view.addObject("type", type);
        return view;
    }
    
    @RequestMapping(value = "/types/edit/{typeId}", method = RequestMethod.POST)
    public ModelAndView edit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("typeId") UUID typeId,
            @ModelAttribute PaymentTypeEditModel params) throws PaymentTypeEditException{
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            PaymentTypeData paymentTypeDataObject = new PaymentTypeData();
            paymentTypeDataObject.setActive("1".equals(params.getTypeActive()));
            paymentTypeDataObject.setDescription(params.getTypeDescription());
            paymentTypeDataObject.setName(params.getTypeName());
            paymentTypeDataObject.setUserId(user.getId());
            context.getPaymentTypeSet()
                    .Edit(typeId,paymentTypeDataObject);
            String buttonSubmitted = request.getParameter("submit-button");
            ModelAndView view = null;
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/types");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/types/edit/" + typeId);
                    break;
                case "3":
                    view = new ModelAndView("redirect:/types/add");
                    break;
            }
            return view;
        } catch (ValidationException e) {
            ModelAndView view = new ModelAndView("types-add");
            view.addObject("errorMessage", "Моля въведете всички задължителни полета.");
            return view;
        }
    }
}
