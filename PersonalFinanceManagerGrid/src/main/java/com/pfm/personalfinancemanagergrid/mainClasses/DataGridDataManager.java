/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanagergrid.mainClasses;

import com.pfm.personalfinancemanagergrid.classes.requestObjects.GridParamObject;
import com.pfm.personalfinancemanagergrid.cache.GridCacheObject;
import com.pfm.personalfinancemanagergrid.cache.ICacheProvider;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author Misho
 */
public class DataGridDataManager {

    private GridParamObject params;
    private ICacheProvider cacheProvider;
    private GridCacheObject cache;

    public DataGridDataManager(GridParamObject params,ICacheProvider provider) {
        this.params = params;
        this.cacheProvider = provider;
        String cacheKey = params.getCid();
        cache = provider.getCache(cacheKey);
        System.out.println(cache.getEntity());
    }

    public String getData() {
        SessionFactory factory = new Configuration().configure().buildSessionFactory();
        Session session = factory.openSession();
        try {
            
            //Class<?> cls = Class.forName(params.getSource());
            //Table table = cls.getAnnotation(javax.persistence.Table.class);
            //char firstLetter = params.getSource().charAt(0);
            //Query query = this.buildQuery(session, params, false);
            //Query q = this.buildQuery(session, params, true);
            //List<Serializable> allResults = query.list();
            //List<Serializable> resultList = q.list();
            //Integer itemsCount = allResults.size();
            //session.close();
            //factory.close();
            //Gson gson = new Gson();
            //DataGridResponseObject<Serializable> resp = new DataGridResponseObject<Serializable>();
            //resp.setData(resultList);
            //resp.setDraw(params.getDraw());
            //resp.setRecordsFiltered(itemsCount);
            //resp.setRecordsTotal(itemsCount);
            //String json = gson.toJson(resp);
            return "";
        } catch (Exception ex) {
            session.close();
            factory.close();
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    /*private Query buildQuery(Session session, GridParamObject params, boolean maxResults) throws ParseException, InstantiationException, IllegalAccessException {
        String source = params.getSource();
        List<ColumnRequestObject> columns = params.getColumns();
        String order = source.charAt(0) + "." + columns.get(params.getOrder().get(0).getColumn()).getData() + " " + params.getOrder().get(0).getDir();
        String where = this.buildWhereStatement(params, source.charAt(0));
        if (!"".equals(where)) {
            where = "where " + where;
        }
        Query q = session.createQuery("From " + source + " " + source.charAt(0) + " " + where + " order by " + order);
        for (TableWhereRequestObject whereObj : params.getTableSettings().getWhereObjects()) {
            replaceQueryParameters(q, whereObj.getColumnType(), whereObj.getColumn(), whereObj.getWhereVal(), whereObj.getWhereType());
        }
        for (ColumnRequestObject column : params.getColumns()) {
            if (!isOptionsColumn(column)) {
                String columnName = column.getData();
                String compareType = column.getFilter().getValue();
                String columnType = column.getFilter().getType();
                String searchVal = column.getSearch().getValue();
                replaceQueryParameters(q, columnType, columnName, searchVal, compareType);
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
        for (TableWhereRequestObject where : params.getTableSettings().getWhereObjects()) {
            statement = buildWhereStatementPart(statement, iterate, firstLetter, where.getColumn(), where.getWhereType(), where.getColumnType(), where.getWhereVal());
            iterate++;
        }
        for (ColumnRequestObject column : params.getColumns()) {
            if (!isOptionsColumn(column)) {
                String columnName = column.getData();
                String columnType = column.getFilter().type;
                String whereCompareType = column.getFilter().getType();
                String whereValue = column.getSearch().getValue();
                statement = buildWhereStatementPart(statement, iterate, firstLetter, columnName, whereCompareType, columnType, whereValue);
                iterate++;
            }
        }
        return statement;
    }

    private String determineParamCompareSign(String whereType, String columnType) {
        String sign = "";
        switch (columnType) {
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

    private String buildWhereStatementPart(String statement, Integer iteration, char firstLetter, String column, String compareType, String columnType, String whereVal) {
        if (!"".equals(whereVal)) {
            if (iteration > 0) {
                statement += " and ";
            }
            String sign = determineParamCompareSign(compareType, columnType);
            if ("uuid".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + column;
            }
            if ("string".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + column;
            }
            if ("int".equals(columnType)) {
                statement += firstLetter + "." + column + " " + sign + " :" + column;
            }
            if ("date".equals(columnType)) {
                if ("=".equals(sign)) {
                    statement += "(DAY(" + firstLetter + "." + column + ")" + sign + ":" + column + "_day)";
                    statement += "and(MONTH(" + firstLetter + "." + column + ")" + sign + ":" + column + "_month)";
                    statement += "and(YEAR(" + firstLetter + "." + column + ")" + sign + ":" + column + "_year)";
                }
                if ("<".equals(sign) || ">".equals(sign)) {
                    statement += firstLetter + "." + column + " " + sign + " :" + column;
                }
            }
        }
        return statement;
    }

    private void replaceQueryParameters(Query q, String paramType, String columnName, String searchVal, String compareType) throws ParseException {
        if ("uuid".equals(paramType)) {
            UUID param = determineQueryParamString(paramType, compareType, searchVal);
            q.setParameter(columnName, param);
        }
        if ("int".equals(paramType)) {
            if (!"".equals(searchVal)) {
                String param = determineQueryParamString(paramType, compareType, searchVal);
                int paramInt = Integer.parseInt(param);
                q.setParameter(columnName, paramInt);
            }
        }
        if ("string".equals(paramType)) {
            if (!"".equals(searchVal)) {
                String param = determineQueryParamString(paramType, compareType, searchVal);
                q.setParameter(columnName, param);
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
                    q.setParameter(columnName + "_day", day);
                    q.setParameter(columnName + "_month", month + 1);
                    q.setParameter(columnName + "_year", year);
                } else if ("gt".equals(compareType) || "lt".equals(compareType)) {
                    q.setParameter(columnName, param);
                }
            }
        }
    }

    private <T> T determineQueryParamString(String columnType, String compareType, String searchVal) throws ParseException {
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

    public boolean isOptionsColumn(ColumnRequestObject column) {
        if (column.getData() == null){
            return true;
        }
        return false;
    }*/
}
