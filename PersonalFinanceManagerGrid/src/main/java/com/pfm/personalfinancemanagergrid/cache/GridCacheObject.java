/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author mihail
 */
public class GridCacheObject {
    private String entity;
    private List<GridCacheColumnObject> columns = new ArrayList<GridCacheColumnObject>();
    private GridCacheColumnOptionsObject optionsColumn;

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public List<GridCacheColumnObject> getColumns() {
        return columns;
    }

    public void setColumns(List<GridCacheColumnObject> columns) {
        this.columns = columns;
    }

    public GridCacheColumnOptionsObject getOptionsColumn() {
        return optionsColumn;
    }

    public void setOptionsColumn(GridCacheColumnOptionsObject optionsColumn) {
        this.optionsColumn = optionsColumn;
    }
    
    public GridCacheColumnObject getColumnCacheByColumnName(String columnName){
        for (GridCacheColumnObject column : columns) {
            if(columnName.equals(column.getColumnName())){
                return column;
            }
        }
        return null;
    }
    
    public GridCacheColumnObject getOptionsColumn(String columnName){
        for (GridCacheColumnObject column : columns) {
            if(column.isOptionsColumn()){
                return column;
            }
        }
        return null;
    }
    
}
