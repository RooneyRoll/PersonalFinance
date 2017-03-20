/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controlleradvisors;

import com.pfm.data.exceptions.UserRegisterException;
import org.springframework.http.HttpStatus;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

/**
 *
 * @author Misho
 */

@ControllerAdvice
class GlobalControllerExceptionHandler {
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex) {
        
        return "404";
    }
    
    @ExceptionHandler(value = UserRegisterException.class)
    public String handleRegister(ModelMap map, UserRegisterException ex) {
        System.err.println("-------------------------------------");
        map.put("errorMessage", ex.getMessage());
        return "result";
    }
}
