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
    public ColumnFilterRequestObject filter;
    public ColumnSearchRequestObject search;

    public ColumnFilterRequestObject getFilter() {
        return filter;
    }

    public void setFilter(ColumnFilterRequestObject filter) {
        this.filter = filter;
    }

    public ColumnSearchRequestObject getSearch() {
        return search;
    }

    public void setSearch(ColumnSearchRequestObject search) {
        this.search = search;
    }

}
