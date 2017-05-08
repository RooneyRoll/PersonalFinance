/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes;

/**
 *
 * @author Misho
 */
public class TableWhereObject {
    private String columnEntityName;
    private String columnType;
    private String whereType;
    private String whereVal;

    public TableWhereObject(String columnEntityName, String whereType, String whereVal, String columnType){
        this.columnEntityName =columnEntityName;
        this.whereType = whereType;
        this.whereVal = whereVal;
        this.columnType = columnType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }
    
    public String getColumnEntityName() {
        return columnEntityName;
    }

    public void setColumnEntityName(String columnEntityName) {
        this.columnEntityName = columnEntityName;
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
