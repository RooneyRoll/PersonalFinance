/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.mainClasses;

import com.google.gson.Gson;
import com.pfm.personalfinancemanagergrid.cache.GridCacheColumnObject;
import com.pfm.personalfinancemanagergrid.classes.requestObjects.GridParamObject;
import com.pfm.personalfinancemanagergrid.cache.GridCacheObject;
import com.pfm.personalfinancemanagergrid.cache.GridCacheTableWhereObject;
import com.pfm.personalfinancemanagergrid.cache.ICacheProvider;
import com.pfm.personalfinancemanagergrid.classes.requestObjects.ColumnRequestObject;
import com.pfm.personalfinancemanagergrid.classes.requestObjects.DataGridResponseObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;
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
    private ICacheProvider cacheProvider;
    private GridCacheObject cache;

    public DataGridDataManager(GridParamObject params, ICacheProvider provider) {
        this.params = params;
        this.cacheProvider = provider;
        String cacheKey = params.getCid();
        cache = provider.getCache(cacheKey);
    }

    public String getData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        try {

            Class<?> cls = Class.forName(cache.getEntity());
            Table table = cls.getAnnotation(javax.persistence.Table.class);
            char firstLetter = cache.getEntity().charAt(0);
            Query query = this.buildQuery(session, params, false);
            Query q = this.buildQuery(session, params, true);
            List<Serializable> allResults = query.list();
            List<Serializable> resultList = q.list();
            List<List<String>> resultArray = new ArrayList<List<String>>();

            for (Serializable serializable : resultList) {
                Field[] fields = cls.getDeclaredFields();
                List<String> innerResult = new ArrayList<String>();
                for (GridCacheColumnObject column : cache.getColumns()) {
                    if (!column.isOptionsColumn()) {
                        for (Field field : fields) {
                            field.setAccessible(true);
                            String cachedFieldName = column.getColumnName();
                            String rootEntityField;                                                   
                            if (cachedFieldName.contains(".")) {
                                String[] innerObjects = cachedFieldName.split("\\.");
                                rootEntityField = innerObjects[0];                                
                            } else {
                                rootEntityField = cachedFieldName;
                            }
                            if (!field.getName().equals(rootEntityField)) {
                                continue;
                            }
                            String value = "";
                            if (column.getColumnName().contains(".")) {
                                String[] innerObjects = cachedFieldName.split("\\.");        
                                Field currentField;
                                Class currentEntity = cls;
                                Object valueHolder = serializable;
                                for (String innerObject : innerObjects) {                                    
                                    currentField = currentEntity.getDeclaredField(innerObject);
                                    currentField.setAccessible(true);
                                    valueHolder = currentField.get(valueHolder);
                                    currentEntity = valueHolder.getClass();
                                }
                                value = valueHolder.toString();
                            } else {
                                value = field.get(serializable).toString();
                            }
                            innerResult.add(value);                           
                        }
                    }
                }
                resultArray.add(innerResult);
            }
            Integer itemsCount = allResults.size();
            session.close();
            factory.close();
            Gson gson = new Gson();
            DataGridResponseObject<Serializable> resp = new DataGridResponseObject<Serializable>();
            resp.setData(resultArray);
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

    private Query buildQuery(Session session, GridParamObject params, boolean maxResults) throws ParseException, InstantiationException, IllegalAccessException {
        String source = cache.getEntity();
        List<ColumnRequestObject> columns = params.getColumns();
        String order = source.charAt(0) + "." + cache.getColumns().get(params.getOrder().get(0).getColumn()).getColumnName() + " " + params.getOrder().get(0).getDir();
        String where = this.buildWhereStatement(params, source.charAt(0));
        if (!"".equals(where)) {
            where = "where " + where;
        }
        Integer iterate = 0;
        Query q = session.createQuery("From " + source + " " + source.charAt(0) + " " + where + " order by " + order);
        for (GridCacheTableWhereObject whereObj : cache.getWheres()) {
            replaceQueryParameters(q, whereObj.getColumnType(), whereObj.getColumnName(), whereObj.getWhereVal(), whereObj.getWhereType(), "w_" + iterate);
            iterate++;
        }
        Integer iter = 0;
        if (iter < params.getColumns().size()) {
            for (ColumnRequestObject column : params.getColumns()) {
                GridCacheColumnObject cachedColumn = cache.getColumns().get(iter);
                if (!cachedColumn.isOptionsColumn() && cachedColumn.isSearchableColumn()) {
                    String columnName = cachedColumn.getColumnName();
                    String compareType = column.getFilter().getValue();
                    String columnType = cachedColumn.getType();
                    String searchVal = column.getSearch().getValue();
                    replaceQueryParameters(q, columnType, columnName, searchVal, compareType, "s_" + iter);
                }
                iter++;
            }
        }
        if (maxResults) {
            q.setMaxResults(params.getLength());
            q.setFirstResult(params.getStart());
        }
        return q;
    }

    private String buildWhereStatement(GridParamObject params, char firstLetter) {
        Integer iterate = 0;
        String statement = "";
        for (GridCacheTableWhereObject where : cache.getWheres()) {
            statement = buildWhereStatementPart(statement, iterate, firstLetter, where.getColumnName(), where.getWhereType(), where.getColumnType(), where.getWhereVal(), "w_");
            iterate++;
        }
        Integer iter = 0;
        if (iter < params.getColumns().size()) {
            for (ColumnRequestObject column : params.getColumns()) {
                GridCacheColumnObject cachedColumn = cache.getColumns().get(iter);
                if (!cachedColumn.isOptionsColumn() && cachedColumn.isSearchableColumn()) {
                    String columnName = cachedColumn.getColumnName();
                    String columnType = cachedColumn.getType();
                    String whereCompareType = column.getFilter().getValue();
                    String whereValue = column.getSearch().getValue();
                    statement = buildWhereStatementPart(statement, iter, firstLetter, columnName, whereCompareType, columnType, whereValue, "s_");
                }
                iter++;
            }
        }
        System.out.println(statement);
        return statement;
    }

    private String determineParamCompareSign(String whereType, String columnType) {
        String sign = "";
        switch (columnType) {
            case "bool":
                sign = "=";
                break;
            case "uuid":
                sign = "=";
                break;
            case "string":
                sign = "like";
                break;
            case "date":
                switch (whereType) {
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
            case "int":
                switch (whereType) {
                    case "st":
                    case "en":
                    case "co":
                        sign = "like";
                        break;
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

    private String buildWhereStatementPart(String statement, Integer iteration, char firstLetter, String column, String compareType, String columnType, String whereVal, String prefix) {
        if (!"".equals(whereVal)) {
            if (iteration > 0) {
                statement += " and ";
            }
            //System.out.println(iteration+"---"+column);
            String sign = determineParamCompareSign(compareType, columnType);
            if ("bool".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + prefix + iteration.toString();
            }
            if ("uuid".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + prefix + iteration.toString();
            }
            if ("string".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + prefix + iteration.toString();
            }
            if ("int".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + prefix + iteration.toString();
            }
            if ("date".equals(columnType)) {
                if ("=".equals(sign)) {
                    statement += "(DAY(" + firstLetter + "." + column + ")" + sign + ":" + prefix + iteration.toString() + "_day)";
                    statement += "and(MONTH(" + firstLetter + "." + column + ")" + sign + ":" + prefix + iteration.toString() + "_month)";
                    statement += "and(YEAR(" + firstLetter + "." + column + ")" + sign + ":" + prefix + iteration.toString() + "_year)";
                }
                if ("<".equals(sign) || ">".equals(sign)) {
                    statement += firstLetter + "." + column + " " + sign + " :" + prefix + iteration.toString();
                }
            }
        }
        return statement;
    }

    private void replaceQueryParameters(Query q, String paramType, String columnName, String searchVal, String compareType, String paramName) throws ParseException {
        if ("bool".equals(paramType)) {
            if (!"".equals(searchVal)) {
                boolean param = determineQueryParamString(paramType, compareType, searchVal);
                q.setParameter(paramName, param);
            }
        }
        if ("uuid".equals(paramType)) {
            if (!"".equals(searchVal)) {
                UUID param = determineQueryParamString(paramType, compareType, searchVal);
                q.setParameter(paramName, param);
            }
        }
        if ("int".equals(paramType)) {
            if (!"".equals(searchVal)) {
                String param = determineQueryParamString(paramType, compareType, searchVal);
                int paramInt = Integer.parseInt(param);
                q.setParameter(paramName, paramInt);
            }
        }
        if ("string".equals(paramType)) {
            if (!"".equals(searchVal)) {
                String param = determineQueryParamString(paramType, compareType, searchVal);
                q.setParameter(paramName, param);
            }
        }
        if ("date".equals(paramType)) {
            if (!"".equals(searchVal)) {
                Date param = determineQueryParamString(paramType, compareType, searchVal);
                Calendar cal = Calendar.getInstance();
                cal.setTime(param);
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);
                if ("eq".equals(compareType)) {
                    q.setParameter(paramName + "_day", day);
                    q.setParameter(paramName + "_month", month + 1);
                    q.setParameter(paramName + "_year", year);
                } else if ("gt".equals(compareType) || "lt".equals(compareType)) {
                    q.setParameter(paramName, param);
                }
            }
        }
    }

    private <T> T determineQueryParamString(String columnType, String compareType, String searchVal) throws ParseException {
        if ("bool".equals(columnType)) {
            Boolean val = Boolean.parseBoolean(searchVal);
            return (T) val;
        }
        if ("uuid".equals(columnType)) {
            UUID val = UUID.fromString(searchVal);
            return (T) val;
        }
        if ("string".equals(columnType)) {
            String val = (String) searchVal;
            switch (compareType) {
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
            return (T) val;
        }
        if ("int".equals(columnType)) {
            String val = (String) searchVal;
            switch (compareType) {
                case "lt":
                case "gt":
                case "eq":
                    break;
            }
            return (T) val;
        }
        if ("date".equals(columnType)) {
            DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss a");
            Date date = formatter.parse(searchVal);
            java.sql.Date dateResult = new java.sql.Date(date.getTime());
            return (T) dateResult;
        }
        return null;
    }
}
