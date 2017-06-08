/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.exceptions;

/**
 *
 * @author mihail
 */
public class PageNotFoundException extends Exception {

    public PageNotFoundException(String message) {
        super(message);
    }
}
