/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.cache.GridCacheProvider;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.PaymentCategoryData;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryEditException;
import com.pfm.exceptions.ValidationException;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.models.paymentCategory.PaymentCategoryAddModel;
import com.pfm.models.paymentCategory.PaymentCategoryEditModel;
import com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.io.Serializable;
import java.util.ArrayList;
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
public class PaymentCategoriesController {

    @RequestMapping(value = "/categories", method = RequestMethod.GET)
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
        columnsList.add(new ColumnSettingsObject("pcatActive", "Активност", "string", true, false));
        columnsList.add(new ColumnSettingsObject("pcatName", "Име", "string", true, false));
        columnsList.add(new ColumnSettingsObject("pcatDescription", "Описание", "string", true, true));
        columnsList.add(new ColumnSettingsObject("pcatId", "id", "uuid", false, false));
        columnsList.add(new ColumnSettingsObject("pcatType.ptypeName", "Тип", "string", true, true));
        whereList.add(new TableWhereObject("pcatUser.userUserid", "eq", user.getId().toString(), "uuid"));
        options.add(new ColumnOption("<i class=\"fa fa-eye\" aria-hidden=\"true\"></i>", "3", "categories/view/{3}"));
        options.add(new ColumnOption("<i class=\"fa fa-pencil-square\" aria-hidden=\"true\"></i>", "3", "categories/edit/{3}"));
        ColumnOptionsObject columnOptions = new ColumnOptionsObject("Действия", options);
        TableSettingsObject tableSettings = new TableSettingsObject(whereList, columnOptions);
        GridCacheProvider cacheProvider = new GridCacheProvider(request.getServletContext());
        DataGridBuilder grid = new DataGridBuilder(PaymentCategories.class, columnsList, tableSettings, columnOptions, cacheProvider);
        String gridHtml = grid.buildHtmlForGrid();
        ModelAndView view = new ModelAndView("categories-manage");
        view.addObject("grid", gridHtml);
        return view;
    }

    @RequestMapping(value = "/categories/add", method = RequestMethod.GET)
    public ModelAndView addIndex(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {
        IpfmContext context = pfmContext.getInstance();
        ModelAndView view = new ModelAndView("categories-add");
        List<PaymentType> types = context
                .getPaymentTypeSet().GetAll();
        view.addObject("types", types);
        return view;
    }

    @RequestMapping(value = "/categories/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute PaymentCategoryAddModel params) throws PaymentCategoryAddException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            PaymentCategoryData categoryDataObject = new PaymentCategoryData();
            categoryDataObject.setActive(true);
            categoryDataObject.setDescription(params.getCategoryDescription());
            categoryDataObject.setName(params.getCategoryName());
            categoryDataObject.setUserId(user.getId());
            categoryDataObject.setType(params.getCategoryType());
            Serializable id = context.getPaymentCategorySet()
                    .Add(categoryDataObject);
            String buttonSubmitted = request.getParameter("submit-button");
            ModelAndView view = null;
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/categories");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/categories/edit/" + id);
                    break;
                case "3":
                    view = new ModelAndView("redirect:/categories/add");
                    break;
            }
            return view;
        } catch (ValidationException e) {
            ModelAndView view = new ModelAndView("categories-add");
            view.addObject("errorMessage", "Моля въведете всички задължителни полета.");
            return view;
        }
    }

    @RequestMapping(value = "/categories/edit/{categoryId}", method = RequestMethod.GET)
    public ModelAndView editIndex(ModelMap map, HttpServletRequest request,
            @PathVariable("categoryId") UUID categoryId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {
        IpfmContext context = pfmContext.getInstance();
        List<PaymentType> types = context
                .getPaymentTypeSet().GetAll();
        PaymentCategory category = context.getPaymentCategorySet().GetById(categoryId);
        ModelAndView view = new ModelAndView("categories-edit");
        view.addObject("category", category);
        view.addObject("types", types);
        return view;
    }

    @RequestMapping(value = "/categories/edit/{categoryId}", method = RequestMethod.POST)
    public ModelAndView edit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("categoryId") UUID categoryId,
            @ModelAttribute PaymentCategoryEditModel params) throws PaymentCategoryEditException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = pfmContext.getInstance();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            PaymentCategoryData categoryDataObject = new PaymentCategoryData();
            categoryDataObject.setActive("1".equals(params.getCategoryActive()));
            categoryDataObject.setDescription(params.getCategoryDescription());
            categoryDataObject.setName(params.getCategoryName());
            categoryDataObject.setUserId(user.getId());
            categoryDataObject.setType(params.getCategoryType());
            System.out.println(params.getCategoryType());
            context.getPaymentCategorySet()
                    .Edit(categoryId, categoryDataObject);
            String buttonSubmitted = request.getParameter("submit-button");
            ModelAndView view = null;
            switch (buttonSubmitted) {
                case "1":
                    view = new ModelAndView("redirect:/categories");
                    break;
                case "2":
                    view = new ModelAndView("redirect:/categories/edit/" + categoryId);
                    break;
                case "3":
                    view = new ModelAndView("redirect:/categories/add");
                    break;
            }
            return view;
        } catch (ValidationException e) {
            ModelAndView view = new ModelAndView("categories-add");
            view.addObject("errorMessage", "Моля въведете всички задължителни полета.");
            return view;
        }
    }
}
