/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.controllers;

import com.google.gson.Gson;
import com.pfm.datagrid.DataGridResponseObject;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import com.pfm.requestObjects.ColumnRequestObject;
import com.pfm.requestObjects.GridParamObject;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Misho
 */
@RestController
public class GridDataController {

    @RequestMapping(value = "/gridData", method = RequestMethod.POST, produces = "application/json")
    public String gridData(HttpServletRequest request,
            HttpServletResponse response,
            @RequestBody GridParamObject params) {
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
            resp.setRecordsTotal(300);
            //for (Serializable entity : resultList) {
            //    Class<?> entityClass = Class.forName(entity.getClass().getName());
            //    System.out.println(entityClass.getName());
            //}
            String json = gson.toJson(resp);
            return json;
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static <T> List<T> createListOfType(Class<T> type) {
        return new ArrayList<T>();
    }

    private String buildWhereStatement(GridParamObject params, char firstLetter) {
        //where c.fileName in :fileName
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
        int currentPage = params.getStart() / params.getLength();
        String source = params.getSource();
        List<ColumnRequestObject> columns = params.getColumns();
        String order = source.charAt(0)+"."+columns.get(params.getOrder().get(0).getColumn()).getData() + " " + params.getOrder().get(0).getDir();
        String where = this.buildWhereStatement(params, source.charAt(0));
        if(!"".equals(where)){
            where = "where "+where;
        }
        Query q = session.createQuery("From " + source + " " + source.charAt(0) + " " + where + " order by " + order);
        for (ColumnRequestObject column : params.getColumns()) {
            String field = column.getData();
            String searchVal = column.getSearch().getValue();
            if (!"".equals(searchVal)) {
                q.setParameter(field, searchVal+"%");
            }
        }
        q.setMaxResults(params.getLength());
        q.setFirstResult(params.getStart());
        return q;
    }

    private Object getSessionFactory() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
