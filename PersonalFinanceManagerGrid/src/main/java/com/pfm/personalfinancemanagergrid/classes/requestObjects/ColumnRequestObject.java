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
public class ColumnRequestObject {
    public String data;
    public String name;
    public String searchable;
    public String orderable;
    public ColumnFilterRequestObject filter;
    public ColumnSearchRequestObject search;

    public ColumnFilterRequestObject getFilter() {
        return filter;
    }

    public void setFilter(ColumnFilterRequestObject filter) {
        this.filter = filter;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSearchable() {
        return searchable;
    }

    public void setSearchable(String searchable) {
        this.searchable = searchable;
    }

    public String getOrderable() {
        return orderable;
    }

    public void setOrderable(String orderable) {
        this.orderable = orderable;
    }

    public ColumnSearchRequestObject getSearch() {
        return search;
    }

    public void setSearch(ColumnSearchRequestObject search) {
        this.search = search;
    }

}
