/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.classes;

import com.google.gson.Gson;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
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

    public DataGridDataManager(GridParamObject params) {
        this.params = params;
    }

    public String getData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        try {
            Class<?> cls = Class.forName(params.getSource());
            Table table = cls.getAnnotation(javax.persistence.Table.class);

            char firstLetter = params.getSource().charAt(0);
            Query query = session.createQuery("select count(*) from " + params.getSource() + " " + firstLetter);
            Long itemsCount = (Long) query.uniqueResult();
            Query q = this.buildQuery(session, params);
            System.out.println("----" + q.getQueryString());
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
        } catch (Exception ex) {
            session.close();
            factory.close();
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private String determineParamCompareSign(ColumnRequestObject column) {
        ColumnFilterRequestObject currentFilter = column.getFilter();
        String sign = "";
        switch (currentFilter.type) {
            case "string":
                sign = "like";
                break;
            case "date":
                switch (currentFilter.getValue()) {
                    case "eq":
                        sign = "=";
                        break;
                    case "lt":
                        sign = "<";
                        break;
                    case "gt":
                        sign = ">";
                        break;
                }
                break;

        }
        return sign;
    }

    private <T> T determineQueryParamString(ColumnRequestObject column, String searchVal) throws InstantiationException, IllegalAccessException, ParseException {
        ColumnFilterRequestObject currentFilter = column.getFilter();
        if ("string".equals(currentFilter.getType())) {
            String val = (String) searchVal;
            switch (currentFilter.getValue()) {
                    case "eq":
                        break;
                    case "co":
                        val = "%" + val + "%";
                        break;
                    case "en":
                        val = "%" + val;
                        break;
                    case "st":
                        val = val + "%";
                        break;
            }
            return (T)val;
        }
        if ("date".equals(currentFilter.getType())) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = formatter.parse(searchVal);
            String textDate = formatter.format(date);
            date = formatter.parse(textDate);
            return (T)date;
        }
        return null;
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
                String sign = determineParamCompareSign(column);
                if (!"date".equals(column.getFilter().type)) {
                    statement += firstLetter + "." + field + " " + sign + " :" + field;
                }
                if ("date".equals(column.getFilter().type)) {
                    statement += firstLetter + "." + field + " " + sign + " :" + field;
                }
                iterate++;
            }
        }
        return statement;
    }

    private Query buildQuery(Session session, GridParamObject params) throws ParseException, InstantiationException, IllegalAccessException {
        String source = params.getSource();
        List<ColumnRequestObject> columns = params.getColumns();
        String order = source.charAt(0) + "." + columns.get(params.getOrder().get(0).getColumn()).getData() + " " + params.getOrder().get(0).getDir();
        String where = this.buildWhereStatement(params, source.charAt(0));
        if (!"".equals(where)) {
            where = "where " + where;
        }
        Query q = session.createQuery("From " + source + " " + source.charAt(0) + " " + where + " order by " + order);
        for (ColumnRequestObject column : params.getColumns()) {
            String field = column.getData();
            String type = column.getFilter().getType();
            String searchVal = column.getSearch().getValue();
            if ("string".equals(type)) {
                if (!"".equals(searchVal)) {
                    String param = determineQueryParamString(column, searchVal);
                    q.setParameter(field, param);
                }
            }
            if ("date".equals(type)) {
                if (!"".equals(searchVal)) {
                    Date param = determineQueryParamString(column, searchVal);
                    System.out.println(param);
                    q.setParameter(field, param);
                }
            }
        }
        q.setMaxResults(params.getLength());
        q.setFirstResult(params.getStart());
        return q;
    }
}
