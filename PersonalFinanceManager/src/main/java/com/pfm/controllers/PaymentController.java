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
import com.pfm.data.entities.User;
import com.pfm.data.exceptions.BasicException;
import com.pfm.exceptions.PageNotFoundException;
import com.pfm.exceptions.ValidationException;
import com.pfm.models.payment.PaymentAddModel;
import com.pfm.models.payment.PaymentEditModel;
import com.pfm.personalfinancemanager.datapostgres.entities.Payments;
import com.pfm.personalfinancemanagergrid.mainClasses.DataGridBuilder;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOption;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnOptionsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.ColumnSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableSettingsObject;
import com.pfm.personalfinancemanagergrid.settingsObject.TableWhereObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author Misho
 */
@Controller
public class PaymentController extends BaseController  {

    @RequestMapping(value = "/payments", method = RequestMethod.GET)
    public ModelAndView index(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = this.getPfmContext();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<ColumnSettingsObject> columnsList = new ArrayList<>();
        List<TableWhereObject> whereList = new ArrayList<>();
        List<ColumnOption> options = new ArrayList<>();
        columnsList.add(new ColumnSettingsObject("pAmount", "Сума", "int", true, true));
        columnsList.add(new ColumnSettingsObject("pDate", "Дата на плащане", "string", true, true));
        columnsList.add(new ColumnSettingsObject("pDescription", "Описание на плащане", "string", true, true));
        columnsList.add(new ColumnSettingsObject("pId", "", "string", false, false));
        columnsList.add(new ColumnSettingsObject("pCategory.pcatType.ptypeName", "Тип", "string", true, true));
        whereList.add(new TableWhereObject("pCategory.pcatUser.userUserid", "eq", user.getId().toString(), "uuid"));
        options.add(new ColumnOption("<i class=\"fa fa-eye\" aria-hidden=\"true\"></i>", "3", "payments/preview/{3}"));
        options.add(new ColumnOption("<i class=\"fa fa-pencil-square\" aria-hidden=\"true\"></i>", "3", "payments/edit/{3}"));
        ColumnOptionsObject columnOptions = new ColumnOptionsObject("Действия", options);
        TableSettingsObject tableSettings = new TableSettingsObject(whereList, columnOptions);
        GridCacheProvider cacheProvider = new GridCacheProvider(request.getServletContext());
        DataGridBuilder grid = new DataGridBuilder(Payments.class, columnsList, tableSettings, columnOptions, cacheProvider);
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
        IpfmContext context = this.getPfmContext();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        List<PaymentCategory> caregories = context.getPaymentCategorySet().GetAllActiveCategoriesForUser(user.getId());
        ModelAndView view = new ModelAndView("payment-add");
        view.addObject("categories", caregories);
        return view;
    }

    @RequestMapping(value = "/payments/add", method = RequestMethod.POST)
    public ModelAndView add(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @ModelAttribute PaymentAddModel params) throws ClassNotFoundException, BasicException {
        try {
            IpfmContext context = this.getPfmContext();
            User user = context.getUserSet().GetByUserName(this.getAuth().getName());
            if (params.getPaymentCategory() == null || "".equals(params.getPaymentAmount())) {
                throw new ValidationException("Payment edit error: required fields not filled.");
            }

            PaymentData PaymentObject = new PaymentData();
            PaymentObject.setActive(true);
            PaymentObject.setDescription(params.getPaymentDescription());
            PaymentObject.setCategory(params.getPaymentCategory());
            try{
            PaymentObject.setConfirmed(this.isPaymentConfirmedByDates(params.getPaymentDate()));
            }catch(ParseException e){
                //TODO handle exception;
            }
            double amount = params.getPaymentAmount();
            if (amount < 0) {
                amount = 0;
            }
            PaymentObject.setAmount(amount);
            PaymentObject.setDate(params.getPaymentDate());
            PaymentObject.setUserId(user.getId());
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
        } catch (ValidationException ex) {
            map.put("errorMessage", "Моля въведете всички задължителни полета.");
            ModelAndView view = new ModelAndView("payment-add");
            return view;
        }
    }

    @RequestMapping(value = "/payments/edit/{paymentId}", method = RequestMethod.GET)
    public ModelAndView editIndex(ModelMap map, HttpServletRequest request,
            @PathVariable("paymentId") UUID paymentId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws PageNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = this.getPfmContext();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        Payment payment = new Payment();
        PaymentCategory category;
        try {
            payment = context
                    .getPaymentSet()
                    .GetById(paymentId);
            category = context
                    .getPaymentCategorySet()
                    .GetById(payment.getCategory());
            if (!category.getUserId().equals(user.getId())) {
                throw new PageNotFoundException("Payment with id:" + paymentId + " for user:" + user.getUserName() + " does not exists.");
            }
        } catch (EntityNotFoundException exc) {
            throw new PageNotFoundException("Payment with id:" + paymentId + " for user:" + user.getUserName() + " does not exists.");
        }
        List<PaymentCategory> caregories = context.getPaymentCategorySet().GetAllActiveCategoriesForUser(user.getId());
        ModelAndView view = new ModelAndView("payment-edit");
        view.addObject("payment", payment);
        view.addObject("categories", caregories);
        return view;
    }

    @RequestMapping(value = "/payments/edit/{paymentId}", method = RequestMethod.POST)
    public ModelAndView edit(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @PathVariable("paymentId") UUID paymentId,
            @ModelAttribute PaymentEditModel params,
            RedirectAttributes redirectAttributes) throws BasicException {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            IpfmContext context = this.getPfmContext();
            User user = context
                    .getUserSet()
                    .GetByUserName(auth.getName());
            if (params.getPaymentCategory() == null || "".equals(params.getPaymentAmount())) {
                throw new ValidationException("Payment edit error: required fields not filled.");
            }
            PaymentData paymentDataObject = new PaymentData();
            paymentDataObject.setActive("1".equals(params.isPaymentActive()));
            double amount = params.getPaymentAmount();
            if (amount < 0) {
                amount = 0;
            }
            paymentDataObject.setAmount(amount);
            paymentDataObject.setCategory(params.getPaymentCategory());
            paymentDataObject.setDate(params.getPaymentDate());
            paymentDataObject.setDescription(params.getPaymentDescription());
            paymentDataObject.setUserId(user.getId());
            try{
            paymentDataObject.setConfirmed(this.isPaymentConfirmedByDates(params.getPaymentDate()));
            }catch(ParseException e){
                //TODO handle exception;
            }
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
        } catch (ValidationException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Моля въведете всички задължителни полета.");
            ModelAndView view = new ModelAndView("redirect:/payments/edit/" + paymentId);
            return view;
        }
    }

    @RequestMapping(value = "/payments/preview/{paymentId}", method = RequestMethod.GET)
    public ModelAndView preview(ModelMap map, HttpServletRequest request,
            @PathVariable("paymentId") UUID paymentId,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws BasicException, PageNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = this.getPfmContext();
        User user = context
                .getUserSet()
                .GetByUserName(auth.getName());
        Payment payment = new Payment();
        PaymentCategory category;
        try {
            payment = context
                    .getPaymentSet()
                    .GetById(paymentId);
            category = context
                    .getPaymentCategorySet()
                    .GetById(payment.getCategory());
            if (!category.getUserId().equals(user.getId())) {
                throw new PageNotFoundException("Payment with id:" + paymentId + " for user:" + user.getUserName() + " does not exists.");
            }
        } catch (EntityNotFoundException Exc) {
            throw new PageNotFoundException("Payment with id:" + paymentId + " for user:" + user.getUserName() + " does not exists.");
        }
        List<PaymentCategory> caregories = context
                .getPaymentCategorySet()
                .GetAllActiveCategoriesForUser(user.getId());
        ModelAndView view = new ModelAndView("payment-preview");
        view.addObject("payment", payment);
        view.addObject("categories", caregories);
        return view;
    }

    @RequestMapping(value = "/payments/status", method = RequestMethod.GET)
    public ModelAndView paymentsStatus(ModelMap map, HttpServletRequest request,
            HttpServletResponse response,
            @RequestParam(value = "error", required = false) String error) throws ClassNotFoundException {

        ModelAndView view = new ModelAndView("payments-status");
        return view;
    }

    private boolean isPaymentConfirmedByDates(Date paymentDate) throws ParseException {
        boolean confirmed = false;
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date currentDate = sdf.parse(sdf.format(now));
        Date pDate = sdf.parse(sdf.format(paymentDate));
        confirmed = currentDate.compareTo(pDate) >= 0;
        return confirmed;
    }
}
