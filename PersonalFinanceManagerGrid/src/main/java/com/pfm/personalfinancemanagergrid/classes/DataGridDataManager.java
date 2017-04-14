/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes;

import com.google.gson.Gson;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Table;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

/**
 *
 * @author Misho
 */
public class DataGridDataManager {
    private GridParamObject params;
    
    public DataGridDataManager(GridParamObject params){
        this.params = params;
    }
    
    public String getData(){
        try {
            Class<?> cls = Class.forName(params.getSource());
            Table table = cls.getAnnotation(javax.persistence.Table.class);
            SessionFactory factory = new Configuration().configure().buildSessionFactory();
            Session session = factory.openSession();
            char firstLetter = params.getSource().charAt(0);
            Query query = session.createQuery("select count(*) from " + params.getSource() + " " + firstLetter);
            Long itemsCount = (Long) query.uniqueResult();
            Query q = this.buildQuery(session, params);
            List<Serializable> resultList = q.list();
            session.close();
            factory.close();
            Gson gson = new Gson();
            DataGridResponseObject<Serializable> resp = new DataGridResponseObject<Serializable>();
            resp.setData(resultList);
            resp.setDraw(params.getDraw());
            resp.setRecordsFiltered(itemsCount);
            resp.setRecordsTotal(itemsCount);
            String json = gson.toJson(resp);
            return json;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }
    
    private String determineParamQueryString(Integer fieldKey, GridParamObject params, String searchVal) {
        ColumnFilterRequestObject currentFilter = params.getFilter().get(fieldKey);
        switch (currentFilter.getValue()) {
            case "eq":

                break;
            case "co":
                searchVal = "%"+searchVal + "%";
                break;
            case "en":
                searchVal = "%"+searchVal;
                break;
            case "st":
                searchVal = searchVal + "%";  
                break;
        }
        return searchVal;
    }

    private String buildWhereStatement(GridParamObject params, char firstLetter) {
        Integer iterate = 0;
        String statement = "";
        for (ColumnRequestObject column : params.getColumns()) {
            String field = column.getData();
            String searchVal = column.getSearch().getValue();
            if (!"".equals(searchVal)) {
                if (iterate > 0) {
                    statement += " and ";
                }
                statement += firstLetter + "." + field + " like :" + field;
                iterate++;
            }
        }
        return statement;
    }

    private Query buildQuery(Session session, GridParamObject params) {
        String source = params.getSource();
        List<ColumnRequestObject> columns = params.getColumns();
        String order = source.charAt(0) + "." + columns.get(params.getOrder().get(0).getColumn()).getData() + " " + params.getOrder().get(0).getDir();
        String where = this.buildWhereStatement(params, source.charAt(0));
        if (!"".equals(where)) {
            where = "where " + where;
        }
        Query q = session.createQuery("From " + source + " " + source.charAt(0) + " " + where + " order by " + order);
        Integer i = 0;
        for (ColumnRequestObject column : params.getColumns()) {
            String field = column.getData();
            String searchVal = column.getSearch().getValue();
            if (!"".equals(searchVal)) {
                String param = determineParamQueryString(i, params, searchVal);
                q.setParameter(field, param);
            }
            i++;
        }
        q.setMaxResults(params.getLength());
        q.setFirstResult(params.getStart());
        return q;
    }
}
