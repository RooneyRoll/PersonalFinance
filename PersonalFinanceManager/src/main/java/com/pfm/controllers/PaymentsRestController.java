/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.Payment;
import com.pfm.data.entities.PaymentType;
import com.pfm.data.entities.User;
import com.pfm.models.payment.PaymentRestParamObject;
import com.pfm.models.payment.PaymentStatisticsResultObject;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Misho
 */
@RestController
public class PaymentsRestController extends BaseController  {
    @RequestMapping(value = "/getPayments", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    public String payments(HttpServletRequest request, @RequestBody PaymentRestParamObject params) {
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date start;
        Date end;
        try {
            start = format.parse(params.getFrom());
            end = format.parse(params.getTo());
            String json = this.buildStatsJsonForInterval(start, end);
            return json;
        } catch (ParseException ex) {
            return "";
        }
    }

    private String buildStatsJsonForInterval(Date start, Date end) {
        List<PaymentStatisticsResultObject> result = new ArrayList<>();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        IpfmContext context = this.getPfmContext();
        User user = context.getUserSet().GetByUserName(auth.getName());
        List<PaymentType> types = context.getPaymentTypeSet().GetAll();
        for (PaymentType type : types) {
            List<Payment> payments = context.getPaymentSet().getAllActiveAndConfirmedPaymentsForUserByPaymentTypeAndInterval(user.getId(), type.getId(), start, end);
            PaymentStatisticsResultObject object = buildValuesStructure(payments, type, start, end);
            result.add(object);
        }
        Gson gson = new Gson();
        String json = gson.toJson(result);
        return json;
    }

    private PaymentStatisticsResultObject buildValuesStructure(List<Payment> payments, PaymentType type, Date start, Date end) {
        Map<Long, Double> map = this.createMapForRange(start, end, payments);
        PaymentStatisticsResultObject object = new PaymentStatisticsResultObject();
        object.setAmounts(map);
        object.setPaymentType(type.getName());
        object.setPaymentTypeId(type.getId());
        return object;
    }

    private Map<Long, Double> createMapForRange(Date startDate, Date endDate, List<Payment> payments) {
        Map<Long, Double> map = new LinkedHashMap<Long, Double>();
        Calendar startCalender = Calendar.getInstance();
        startCalender.setTime(startDate);
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(endDate);
        Double total = 0.0;
        while (startCalender.compareTo(endCalendar) <= 0) {
            Date key = startCalender.getTime();
            int day = startCalender.get(Calendar.DAY_OF_MONTH);
            if (day == 1) {
                total = 0.0;
            }
            for (Payment payment : payments) {
                Double amount = payment.getAmount();
                Date paymentDate = payment.getDate();
                if (key.compareTo(paymentDate) == 0) {
                    total = total + amount;
                }
            }
            Long dateRepresent = key.getTime();
            map.put(dateRepresent, total);
            startCalender.add(Calendar.DATE, 1);
        }
        return map;
    }
}
