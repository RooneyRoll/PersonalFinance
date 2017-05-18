/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.cache.GridCacheProvider;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.PaymentData;
import com.pfm.data.entities.Payment;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.BasicException;
import com.pfm.models.payment.PaymentAddModel;
import com.pfm.models.payment.PaymentEditModel;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.personalfinancemanager.datapostgres.entities.Payments;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
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
public class PaymentController {

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {
Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<ColumnSettingsObject> columnsList = new ArrayList<ColumnSettingsObject>();
        List<TableWhereObject> whereList = new ArrayList<TableWhereObject>();
        List<ColumnOption> options = new ArrayList<ColumnOption>();
        columnsList.add(new ColumnSettingsObject("pAmount", "Сума", "int", true,true));
        columnsList.add(new ColumnSettingsObject("pDate", "Дата на плащане", "string", true,true));
        columnsList.add(new ColumnSettingsObject("pDescription", "Описание на плащане", "string", true,true));
        columnsList.add(new ColumnSettingsObject("pId", "", "string", false,false));
        //whereList.add(new TableWhereObject("ptypeUser", "eq", user.getId().toString(), "uuid"));
        options.add(new ColumnOption("<i class=\"fa fa-eye\" aria-hidden=\"true\"></i>","3","payments/edit/{3}"));
        options.add(new ColumnOption("<i class=\"fa fa-pencil-square\" aria-hidden=\"true\"></i>","3","payments/edit/{3}"));
        ColumnOptionsObject columnOptions = new ColumnOptionsObject("Действия", options);
        TableSettingsObject tableSettings = new TableSettingsObject(whereList, columnOptions);
        GridCacheProvider cacheProvider = new GridCacheProvider(request.getServletContext());
        DataGridBuilder grid = new DataGridBuilder(Payments.class, columnsList, tableSettings, columnOptions,cacheProvider);
        String gridHtml = grid.buildHtmlForGrid();
        ModelAndView view = new ModelAndView("payment-manage");
        view.addObject("grid", gridHtml);
        return view;
    }

    @RequestMapping(value = "/payments/add", method = RequestMethod.GET)
    public ModelAndView addIndex(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<PaymentCategory> caregories = context.getPaymentCategorySet().GetAllActiveCategoriesForUser(user.getId());
        List<PaymentType> types = context.getPaymentTypeSet().GetAllActiveTypesForUser(user.getId());
        ModelAndView view = new ModelAndView("payment-add");
        view.addObject("categories", caregories);
        view.addObject("types", types);
        return view;
    }

    @RequestMapping(value = "/payments/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute PaymentAddModel params) throws ClassNotFoundException, BasicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        PaymentData PaymentObject = new PaymentData();
        PaymentObject.setActive(true);
        PaymentObject.setDescription(params.getPaymentDescription());
        PaymentObject.setCategory(params.getPaymentCategory());
        PaymentObject.setType(params.getPaymentType());
        PaymentObject.setAmount(params.getPaymentAmount());
        PaymentObject.setDate(new Date());
        UUID id = context.getPaymentSet().Add(PaymentObject);
        String buttonSubmitted = request.getParameter("submit-button");
        ModelAndView view = null;
        switch (buttonSubmitted) {
            case "1":
                view = new ModelAndView("redirect:/payments");
                break;
            case "2":
                view = new ModelAndView("redirect:/payments/edit/" + id);
                break;
            case "3":
                view = new ModelAndView("redirect:/payments/add");
                break;
        }
        return view;
    }
    
    @RequestMapping(value = "/payments/edit/{paymentId}", method = RequestMethod.GET)
    public ModelAndView editIndex(ModelMap map, HttpServletRequest request,
            @PathVariable("paymentId") UUID paymentId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws BasicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        Payment payment = context.getPaymentSet().GetById(paymentId);
        List<PaymentCategory> caregories = context.getPaymentCategorySet().GetAllActiveCategoriesForUser(user.getId());
        List<PaymentType> types = context.getPaymentTypeSet().GetAllActiveTypesForUser(user.getId());
        ModelAndView view = new ModelAndView("payment-edit");
        view.addObject("payment", payment);
        view.addObject("categories", caregories);
        view.addObject("types", types);
        return view;
    }
    
    @RequestMapping(value = "/payments/edit/{paymentId}", method = RequestMethod.POST)
    public ModelAndView edit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("paymentId") UUID paymentId,
            @ModelAttribute PaymentEditModel params) throws BasicException {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            Payment currentPayment = pfmContext.getInstance().getPaymentSet().GetById(paymentId);
            PaymentData paymentDataObject = new PaymentData();
            paymentDataObject.setActive("1".equals(params.isPaymentActive()));
            paymentDataObject.setAmount(params.getPaymentAmount());
            paymentDataObject.setCategory(params.getPaymentCategory());
            paymentDataObject.setDate(currentPayment.getDate());
            paymentDataObject.setDescription(params.getPaymentDescription());
            paymentDataObject.setType(params.getPaymentType());
            context.getPaymentSet()
                    .Edit(paymentId, paymentDataObject);
            String buttonSubmitted = request.getParameter("submit-button");
            ModelAndView view = null;
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/payments");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/payments/edit/" + paymentId);
                    break;
                case "3":
                    view = new ModelAndView("redirect:/payments/add");
                    break;
            }
            return view;
    }
}