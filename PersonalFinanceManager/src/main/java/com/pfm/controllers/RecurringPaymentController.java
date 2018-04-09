/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.cache.GridCacheProvider;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.RecurringBudgetPaymentData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.RecurringBudgetPayment;
import com.pfm.data.entities.RecurringType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.BasicException;
import com.pfm.enums.RecurringTypes;
import com.pfm.exceptions.PageNotFoundException;
import com.pfm.exceptions.ValidationException;
import com.pfm.models.recurringBudgetPayment.RecurringBudgetPaymentAddModel;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.personalfinancemanager.datapostgres.entities.RecurringBudgetPayments;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import javax.persistence.EntityNotFoundException;
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
 * @author Misho
 */
@Controller
public class RecurringPaymentController {

    @RequestMapping(value = "/recurringPayments", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<ColumnSettingsObject> columnsList = new ArrayList<>();
        List<TableWhereObject> whereList = new ArrayList<>();
        List<ColumnOption> options = new ArrayList<>();
        columnsList.add(new ColumnSettingsObject("rbpTitle", "Име", "string", true, true));
        columnsList.add(new ColumnSettingsObject("rbpRecType.rtName", "Тип на период", "string", true, true));
        columnsList.add(new ColumnSettingsObject("rbpPeriods", "Периоди", "int", true, true));
        columnsList.add(new ColumnSettingsObject("rbpAmount", "Сума за период", "double", true, true));
        columnsList.add(new ColumnSettingsObject("rbpCategory.pcatName", "Категория", "string", true, true));
        columnsList.add(new ColumnSettingsObject("rbpDateStart", "Начална дата", "Date", true, true));
        columnsList.add(new ColumnSettingsObject("rbpId", "", "UUID", false, false));
        whereList.add(new TableWhereObject("rbpCategory.pcatUser.userUserid", "eq", user.getId().toString(), "uuid"));
        options.add(new ColumnOption("<i class=\"fa fa-eye\" aria-hidden=\"true\"></i>", "6", "recurringPayments/preview/{6}"));
        options.add(new ColumnOption("<i class=\"fa fa-pencil-square\" aria-hidden=\"true\"></i>", "6", "recurringPayments/edit/{6}"));
        ColumnOptionsObject columnOptions = new ColumnOptionsObject("Действия", options);
        TableSettingsObject tableSettings = new TableSettingsObject(whereList, columnOptions);
        GridCacheProvider cacheProvider = new GridCacheProvider(request.getServletContext());
        DataGridBuilder grid = new DataGridBuilder(RecurringBudgetPayments.class, columnsList, tableSettings, columnOptions, cacheProvider);
        String gridHtml = grid.buildHtmlForGrid();
        ModelAndView view = new ModelAndView("user-budget-recurring-payments-manage");
        view.addObject("grid", gridHtml);
        return view;
    }

    @RequestMapping(value = "/recurringPayments/add", method = RequestMethod.GET)
    public ModelAndView addRecurringPayment(ModelMap map, HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<RecurringType> recurringTypes = context
                .getRecurringTypeSet()
                .GetAll();
        List<PaymentCategory> caregories = context
                .getPaymentCategorySet()
                .GetAllActiveCategoriesForUser(user.getId());
        ModelAndView view = new ModelAndView("user-budget-recurring-payment-add");
        view.addObject("recTypes", recurringTypes);
        view.addObject("categories", caregories);
        view.addObject("daily", RecurringTypes.Daily.getValue());
        view.addObject("weekly", RecurringTypes.Weekly.getValue());
        view.addObject("monthly", RecurringTypes.Monthly.getValue());
        view.addObject("yearly", RecurringTypes.Yearly.getValue());
        return view;
    }

    @RequestMapping(value = "/recurringPayments/add", method = RequestMethod.POST)
    public ModelAndView addRecurringPayment(
            ModelMap map,
            HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute RecurringBudgetPaymentAddModel params) throws BasicException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        try {
            if (params.getPaymentCategory() == null) {
                throw new ValidationException("Recurring payment add error: required fields not filled.");
            }
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());

            RecurringBudgetPaymentData data = new RecurringBudgetPaymentData();
            data.setActive(true);
            data.setFinished(false);
            data.setAmount(params.getPaymentSinglePeriodAmount());
            data.setDescription(params.getPaymentDescription());
            data.setFinalAmount(params.getPaymentFinalAmount());
            data.setFinishDate(params.getPaymentFinishDate());
            data.setInitialAmount(params.getPaymentInitialAmount());
            data.setMissPerPeriods(params.getMissPerPeriods());
            data.setPaymentCategoryId(params.getPaymentCategory());
            data.setPeriods(params.getPeriodsCount());
            data.setRecurringType(params.getPaymentRecuringType());
            data.setStartDate(params.getPaymentStartDate());
            data.setTitle(params.getPaymentName());
            data.setUserId(user.getId());
            UUID id = context.getRecurringBudgetPaymentSet()
                    .Add(data);
            ModelAndView view = new ModelAndView("recurringPayments");
            return view;
        } catch (ValidationException ex) {
            map.put("errorMessage", "Моля въведете всички задължителни полета.");
            ModelAndView view = new ModelAndView("user-budget-recurring-payment-add");
            return view;
        }
    }

    @RequestMapping(value = "/recurringPayments/edit/{recPaymentId}", method = RequestMethod.GET)
    public ModelAndView editRecurringPayment(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("recPaymentId") UUID recPaymentId) throws PageNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(recPaymentId);
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        RecurringBudgetPayment recurringPayment;
        List<RecurringType> recurringTypes = new ArrayList<>();
        try {
            recurringPayment = context
                    .getRecurringBudgetPaymentSet()
                    .GetById(recPaymentId);
            recurringTypes = context
                    .getRecurringTypeSet()
                    .GetAll();
            if (!recurringPayment.getUserId().equals(user.getId())) {
                throw new PageNotFoundException("recurring payment with id:" + recPaymentId + " for user:" + user.getUserName() + " does not exists.");
            }
        } catch (EntityNotFoundException exc) {
            throw new PageNotFoundException("recurring payment with id:" + recPaymentId + " for user:" + user.getUserName() + " does not exists.");
        }
        ModelAndView view = new ModelAndView("user-budget-recurring-payment-edit");
        view.addObject("recPayment", recurringPayment);
        view.addObject("recTypes", recurringTypes);
        return view;
    }
}
