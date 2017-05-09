/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.settingsObject;

import java.util.List;

/**
 *
 * @author Misho
 */
public class ColumnOptionsObject {
    private String tableFieldName;
    private List<ColumnOption> options;

    public ColumnOptionsObject(String tableFieldName,List<ColumnOption> optionsContent){
        this.tableFieldName = tableFieldName;
        this.options = optionsContent;
    }
    
    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

    public List<ColumnOption> getOptions() {
        return options;
    }

    public void setOptions(List<ColumnOption> optionsContent) {
        this.options = optionsContent;
    }
    

}
