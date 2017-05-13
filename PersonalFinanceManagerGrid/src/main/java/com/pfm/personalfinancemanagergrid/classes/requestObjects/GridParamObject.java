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
public class GridParamObject {
    public List<ColumnRequestObject> columns;
    public Integer draw;
    public Integer length;
    public Integer start;
    public String source;
    public String cid;
    public ColumnSearchRequestObject search;
    public List<ColumnOrderRequestObject> order;

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }
    
    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public List<ColumnOrderRequestObject> getOrder() {
        return order;
    }

    public void setOrder(List<ColumnOrderRequestObject> order) {
        this.order = order;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<ColumnRequestObject> getColumns() {
        return columns;
    }

    public void setColumns(List<ColumnRequestObject> columns) {
        this.columns = columns;
    }

    public ColumnSearchRequestObject getSearch() {
        return search;
    }

    public void setSearch(ColumnSearchRequestObject search) {
        this.search = search;
    }
}
