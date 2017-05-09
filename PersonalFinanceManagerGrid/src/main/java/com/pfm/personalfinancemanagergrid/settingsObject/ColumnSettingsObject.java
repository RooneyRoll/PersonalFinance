/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.settingsObject;

/**
 *
 * @author Misho
 */
public class ColumnSettingsObject {

    private String entityFieldName;
    private String tableFieldName;
    private boolean allowedField;
    private String fieldType;

    public ColumnSettingsObject(String entityFieldName,String tableFieldName,String fieldType,boolean allowed) {
        this.allowedField = allowed;
        this.fieldType = fieldType;
        this.tableFieldName = tableFieldName;
        this.entityFieldName = entityFieldName;
    }

    public boolean isAllowedField() {
        return allowedField;
    }

    public void setAllowedField(boolean allowedField) {
        this.allowedField = allowedField;
    }

    public String getFieldType() {
        return fieldType;
    }

    public void setFieldType(String fieldType) {
        this.fieldType = fieldType;
    }

    public String getEntityFieldName() {
        return entityFieldName;
    }

    public void setEntityFieldName(String entityFieldName) {
        this.entityFieldName = entityFieldName;
    }

    public String getTableFieldName() {
        return tableFieldName;
    }

    public void setTableFieldName(String tableFieldName) {
        this.tableFieldName = tableFieldName;
    }

}
