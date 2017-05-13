/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.cache;

import java.util.List;

/**
 *
 * @author mihail
 */
public class GridCacheColumnObject {
    public String type;
    public String visibleName;
    public String columnName; 
    public boolean allowed;
    public boolean OptionsColumn;
    public boolean searchableColumn;
    private List<GridCacheColumnOption> options;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getVisibleName() {
        return visibleName;
    }

    public void setVisibleName(String visibleName) {
        this.visibleName = visibleName;
    }

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public boolean isAllowed() {
        return allowed;
    }

    public void setAllowed(boolean allowed) {
        this.allowed = allowed;
    }

    public boolean isOptionsColumn() {
        return OptionsColumn;
    }

    public void setOptionsColumn(boolean OptionsColumn) {
        this.OptionsColumn = OptionsColumn;
    }

    public List<GridCacheColumnOption> getOptions() {
        return options;
    }

    public void setOptions(List<GridCacheColumnOption> options) {
        this.options = options;
    }

    public boolean isSearchableColumn() {
        return searchableColumn;
    }

    public void setSearchableColumn(boolean searchableColumn) {
        this.searchableColumn = searchableColumn;
    }
    
}
