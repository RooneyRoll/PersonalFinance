/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;
import com.pfm.data.context.IpfmContext;
import com.pfm.data.entities.PaymentType;
import com.pfm.personalfinancemanager.datapostgres.context.pfmContext;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author Misho
 */
@Controller
public class DefaultController {

    @RequestMapping(value ={ "/","/home"}, method = RequestMethod.GET)
    public String index(ModelMap map, HttpServletResponse response, HttpServletRequest request) throws ClassNotFoundException {
        IpfmContext context = pfmContext.getInstance();
            List<PaymentType> list = context.getPaymentTypeSet().GetAll();
        return "home";
    }
    

    
    
    
}
