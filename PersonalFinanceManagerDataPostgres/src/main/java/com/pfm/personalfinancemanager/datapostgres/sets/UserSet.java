/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.sets;
import com.pfm.data.entities.User;
import com.pfm.data.data.UserData;
import com.pfm.data.sets.IUserSet;
import com.pfm.personalfinancemanager.datapostgres.sets.base.BaseSet;
import org.hibernate.SessionFactory;

/**
 * @author Misho
 */
public class UserSet extends BaseSet<User,User> implements IUserSet {

    public UserSet(SessionFactory factory) {
        super(factory);
    }

    @Override
    protected User convertEntityToDto(User Entity) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected User[] convertEntititiesToDtoArray(User[] EntityArray) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected User convertDtoToEntity(User Dto) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User[] GetAll() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public User GetById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Add(UserData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Edit(int id, UserData data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    
}
