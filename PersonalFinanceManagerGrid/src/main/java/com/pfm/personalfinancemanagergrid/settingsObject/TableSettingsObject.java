/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.settingsObject;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Misho
 */
public class TableSettingsObject {
    private List<TableWhereObject> whereObjects = new ArrayList<TableWhereObject>();
    private ColumnOptionsObject options;

    public TableSettingsObject(List<TableWhereObject> whereObjects,ColumnOptionsObject options ){
        this.whereObjects = whereObjects;
        this.options = options;
    }
    
    public List<TableWhereObject> getWhereObjects() {
        return whereObjects;
    }

    public void setWhereObjects(List<TableWhereObject> whereObjects) {
        this.whereObjects = whereObjects;
    }

    public ColumnOptionsObject getOptions() {
        return options;
    }

    public void setOptions(ColumnOptionsObject options) {
        this.options = options;
    }
}
