/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.cache;

/**
 *
 * @author mihail
 */
public class GridCacheTableWhereObject {
    public String columnName;
    public String columnType;
    public String whereType;
    public String whereVal;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getWhereType() {
        return whereType;
    }

    public void setWhereType(String whereType) {
        this.whereType = whereType;
    }

    public String getWhereVal() {
        return whereVal;
    }

    public void setWhereVal(String whereVal) {
        this.whereVal = whereVal;
    }
    
    
}
