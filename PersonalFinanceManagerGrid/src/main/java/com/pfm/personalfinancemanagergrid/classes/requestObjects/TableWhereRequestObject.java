/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes.requestObjects;

/**
 *
 * @author Misho
 */
public class TableWhereRequestObject {
    public String column;
    public String whereType;
    public String columnType;
    public String whereVal;

    public String getColumn() {
        return column;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public String getWhereType() {
        return whereType;
    }

    public void setWhereType(String whereType) {
        this.whereType = whereType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getWhereVal() {
        return whereVal;
    }

    public void setWhereVal(String whereVal) {
        this.whereVal = whereVal;
    }
    
}
