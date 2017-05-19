/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.pfm.cache.GridCacheProvider;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.data.CategoryBudgetData;
import com.pfm.data.entities.CategoryBudget;
import com.pfm.data.entities.PaymentCategory;
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.PaymentCategory.PaymentCategoryAddException;
import com.pfm.models.categoryDetails.CategoryBudgetAddModel;
import com.pfm.models.categoryDetails.CategoryBudgetEditModel;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import com.pfm.personalfinancemanager.datapostgres.views.VCategoryBudgets;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
 * @author Admin
 */
@Controller
public class PaymentCategoryBudgetController {

    @RequestMapping(value = "/categoryBudget", method = RequestMethod.GET)
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
        columnsList.add(new ColumnSettingsObject("cname", "Име", "string", true, true));
        columnsList.add(new ColumnSettingsObject("amount", "Лимит", "int", true, true));
        columnsList.add(new ColumnSettingsObject("id", "id", "uuid", true, false));
        options.add(new ColumnOption("<i class=\"fa fa-eye\" aria-hidden=\"true\"></i>", "3", "types/view/{3}"));
        options.add(new ColumnOption("<i class=\"fa fa-pencil-square\" aria-hidden=\"true\"></i>", "3", "types/edit/{3}"));
        ColumnOptionsObject columnOptions = new ColumnOptionsObject("Действия", options);
        TableSettingsObject tableSettings = new TableSettingsObject(whereList, columnOptions);
        GridCacheProvider cacheProvider = new GridCacheProvider(request.getServletContext());
        DataGridBuilder grid = new DataGridBuilder(VCategoryBudgets.class, columnsList, tableSettings, columnOptions, cacheProvider);
        String gridHtml = grid.buildHtmlForGrid();
        ModelAndView view = new ModelAndView("category-details-manage");

        view.addObject("grid", gridHtml);
        return view;
    }

    @RequestMapping(value = "/categoryBudget/add", method = RequestMethod.GET)
    public ModelAndView addIndex(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PaymentCategoryAddException {
        Map<String, UUID> categoriesMap = new HashMap<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());

        List<PaymentCategory> categories = context.getPaymentCategorySet()
                .getActiveCategoriesByUserIdAndActiveAndWithNoDetailsAdded(user.getId(), true);
        
        for (PaymentCategory category : categories) {
            categoriesMap.put(category.getName(), category.getId());
        }
        ModelAndView view = new ModelAndView("category-details-add");
        List<String> list = Arrays.asList("one", "two", "three");
        view.addObject("categoriesMap", categories);
        return view;

//        return new ModelAndView("categories-add");
    }

    @RequestMapping(value = "/categoryBudget/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute CategoryBudgetAddModel params) throws PaymentCategoryAddException {
//        try {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
//            User user = context
//                    .getUserSet()
//                    .GetByUserName(auth.getName());
        CategoryBudgetData categoryData = new CategoryBudgetData();
        categoryData.setAmount(Double.valueOf(params.getAmount()));
        categoryData.setCategoryid(params.getCategoryId());
        if (params.getFromDate() == null) {

        }

        if (params.getToDate() == null) {
        }

        Serializable id = context.getCategoryDetailSet()
                .Add(categoryData);
        String buttonSubmitted = request.getParameter("submit-button");
        ModelAndView view = null;
        switch (buttonSubmitted) {
            case "1":
                view = new ModelAndView("redirect:/categoryBudget");
                break;
            case "2":
                view = new ModelAndView("redirect:/categoryBudget/edit/" + id);
                break;
            case "3":
                view = new ModelAndView("redirect:/categoryBudget/add");
                break;
        }
        return view;
//        } catch (ValidationException e) {
//            ModelAndView view = new ModelAndView("categories-add");
//            view.addObject("errorMessage", "Моля въведете всички задължителни полета.");
//            return view;
//        }
    }

    @RequestMapping(value = "/categoryBudget/edit/{categoryId}", method = RequestMethod.GET)
    public ModelAndView editIndex(ModelMap map, HttpServletRequest request,
            @PathVariable("categoryId") UUID categoryId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) {
        IpfmContext context = pfmContext.getInstance();
        CategoryBudget categoryDetail = context.getCategoryDetailSet().GetById(categoryId);
        ModelAndView view = new ModelAndView("categories-edit");
        CategoryBudgetEditModel model = new CategoryBudgetEditModel();
//        model.setIsActive("1");
        model.setAmount(categoryDetail.getAmount());
        model.setFromDate(categoryDetail.getFromDate());
        model.setToDate(categoryDetail.getToDate());
        model.setCategoryId(categoryDetail.getCategoryId());
        view.addObject("categoryBudget", categoryDetail);
        return view;
    }

    @RequestMapping(value = "/categoryBudget/edit/{categoryId}", method = RequestMethod.POST)
    public ModelAndView edit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("categoryId") UUID categoryDetailId,
            @ModelAttribute CategoryBudgetEditModel params) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = pfmContext.getInstance();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        CategoryBudgetData categoryDetailDataObject = new CategoryBudgetData();
//            categoryDataObject.setActive("1".equals(params.getCategoryActive()) ? true : false);
        categoryDetailDataObject.setAmount(params.getAmount());
        categoryDetailDataObject.setFromDate(params.getFromDate());
        categoryDetailDataObject.setToDate(params.getToDate());
        categoryDetailDataObject.setCategoryid(params.getCategoryId());
        context.getCategoryDetailSet()
                .Edit(categoryDetailId, categoryDetailDataObject);
        String buttonSubmitted = request.getParameter("submit-button");
        ModelAndView view = null;
        switch (buttonSubmitted) {
            case "1":
                view = new ModelAndView("redirect:/categoryBudget");
                break;
            case "2":
                view = new ModelAndView("redirect:/categoryBudget/edit/" + categoryDetailId);
                break;
            case "3":
                view = new ModelAndView("redirect:/categoryBudget/add");
                break;
        }
        return view;

    }

}
