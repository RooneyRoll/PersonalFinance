/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes.requestObjects;

import java.util.List;

/**
 *
 * @author Misho
 */
public class TableSettingsRequestObject {
    public List<TableWhereRequestObject> where;
    public List<TableWhereRequestObject> getWhereObjects() {
        return where;
    }

    public void setWhereObjects(List<TableWhereRequestObject> whereObjects) {
        this.where = whereObjects;
    }
    
}
