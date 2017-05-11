/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.cache;

import com.pfm.personalfinancemanagergrid.settingsObject.*;
import java.util.List;

/**
 *
 * @author Misho
 */
public class GridCacheColumnOptionsObject {
    private String name;
    private List<GridCacheColumnOption> options;
    
    public String getTableFieldName() {
        return name;
    }

    public void setTableFieldName(String tableFieldName) {
        this.name = tableFieldName;
    }

    public List<GridCacheColumnOption> getOptions() {
        return options;
    }

    public void setOptions(List<GridCacheColumnOption> optionsContent) {
        this.options = optionsContent;
    }
    

}
