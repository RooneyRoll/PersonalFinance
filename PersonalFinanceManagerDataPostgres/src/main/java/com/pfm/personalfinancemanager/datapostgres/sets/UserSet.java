/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;

import com.pfm.data.entities.User;
import com.pfm.data.data.UserData;
import com.pfm.data.exceptions.UserRegisterException;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.entities.Users;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

/**
 * @author Misho
 */
public class UserSet extends BaseSet<Users, User, UserData> implements IUserSet {

    public UserSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected User convertEntityToDto(Users Entity) {
        User userObject = new User();
        userObject.setUserName(Entity.getUserUsername());
        userObject.setPassword(Entity.getUserPassword());
        userObject.setMiddleName(Entity.getUserMiddlename());
        userObject.setLastName(Entity.getUserLastname());
        userObject.setId(Entity.getUserUserid().toString());
        userObject.setFirstName(Entity.getUserFirstname());
        userObject.setEnabled(Entity.getUserEnabled());
        userObject.setEmail(Entity.getUserEmail());
        return userObject;
    }

    @Override
    protected List<User> convertEntititiesToDtoArray(List<Users> EntityArray) {
        List<User> userList = new ArrayList<User>();
        for (Users next : EntityArray) {
            User userObject = new User();
            userObject.setUserName(next.getUserUsername());
            userObject.setPassword(next.getUserPassword());
            userObject.setMiddleName(next.getUserMiddlename());
            userObject.setLastName(next.getUserLastname());
            userObject.setId(next.getUserUserid().toString());
            userObject.setFirstName(next.getUserFirstname());
            userObject.setEnabled(next.getUserEnabled());
            userObject.setEmail(next.getUserEmail());
            userList.add(userObject);
        }
        return userList;
    }

    @Override
    protected Users convertDtoDataToEntity(UserData DtoData) {
        Users userEntity = new Users();
        userEntity.setUserEmail(DtoData.getEmail());
        userEntity.setUserEnabled(DtoData.isEnabled());
        userEntity.setUserFirstname(DtoData.getFirstName());
        userEntity.setUserLastname(DtoData.getLastName());
        userEntity.setUserMiddlename(DtoData.getMiddleName());
        userEntity.setUserPassword(DtoData.getPassword());
        userEntity.setUserUsername(DtoData.getUserName());
        return userEntity;
    }

    @Override
    public List<User> GetAll() {
        Session session = this.getSessionFactory().openSession();
        session.beginTransaction();
        Query q = session.createQuery("From Users");
        List<Users> resultList = q.list();
        List<User> userObjects = convertEntititiesToDtoArray(resultList);
        session.close();
        return userObjects;
    }

    @Override
    public User GetById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @param data
     * @throws UserRegisterException
     */
    @Override
    public Serializable Add(UserData data) throws UserRegisterException {
        Session session = this.getSessionFactory().openSession();
        if (this.userExists(data.getUserName(), session)) {
            throw new UserRegisterException("User already registered");
        }
        session.beginTransaction();
        Users userEntity = convertDtoDataToEntity(data);
        Serializable id = session.save(userEntity);
        session.getTransaction().commit();
        session.close();
        return id;
    }

    @Override
    public void Edit(int id, UserData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private boolean userExists(String username, Session session) {
        Query q = session.createQuery("From Users u where u.userUsername = :username", Users.class)
                .setParameter("username", username);
        boolean exists = false;
        if (q.getResultList().size() > 0) {
            exists = true;
        }
        return exists;
    }

}
