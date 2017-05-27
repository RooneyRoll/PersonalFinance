/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.data.UserRoleData;
import com.pfm.data.entities.UserRole;
import com.pfm.data.exceptions.BasicException;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.data.sets.IUserRoleSet;
import com.pfm.personalfinancemanager.datapostgres.entities.UserRoles;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * @author Misho
 */
public class UserRoleSet extends BaseSet<UserRoles, UserRole, UserRoleData> implements IUserRoleSet {

    public UserRoleSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected UserRole convertEntityToDto(UserRoles Entity) {
        UserRole userObject = new UserRole();
        userObject.setId(Entity.getUserRoleId());
        userObject.setUserName(Entity.getUsername());
        userObject.setUserRole(Entity.getUserRole());
        return userObject;
    }

    @Override
    protected List<UserRole> convertEntititiesToDtoArray(List<UserRoles> EntityArray) {
        List<UserRole> userRoleList = new ArrayList<UserRole>();
        for (UserRoles next : EntityArray) {
            UserRole userObject = new UserRole();
            userObject.setId(next.getUserRoleId());
            userObject.setUserName(next.getUsername());
            userObject.setUserRole(next.getUserRole());
            userRoleList.add(userObject);
        }
        return userRoleList;
    }

    @Override
    protected UserRoles convertDtoDataToEntity(UserRoleData DtoData) {
        UserRoles userRoleEntity = new UserRoles();
        userRoleEntity.setUserRole(DtoData.getUserRole());
        userRoleEntity.setUsername(DtoData.getUserName());
        return userRoleEntity;
    }

    @Override
    public Serializable Add(UserRoleData data) throws UserRegisterException {
        Serializable id;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            UserRoles useRoleEntity = convertDtoDataToEntity(data);
            id = session.save(useRoleEntity);
            session.getTransaction().commit();
        }
        return id;
    }

    @Override
    public List<UserRole> GetAll() {
        List<UserRole> userRoleObjects;
        try (Session session = this.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query q = session.createQuery("From UserRoles");
            List<UserRoles> resultList = q.list();
            userRoleObjects = convertEntititiesToDtoArray(resultList);
        }
        return userRoleObjects;
    }

    @Override
    public UserRole GetById(UUID id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(Serializable id, UserRoleData data) throws BasicException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(Serializable id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Serializable AddOrUpdate(UserRoleData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }



}
