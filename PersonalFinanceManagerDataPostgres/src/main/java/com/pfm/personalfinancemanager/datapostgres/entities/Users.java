/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misho
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUserUsername", query = "SELECT u FROM Users u WHERE u.userUsername = :userUsername")
    , @NamedQuery(name = "Users.findByUserPassword", query = "SELECT u FROM Users u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "Users.findByUserEmail", query = "SELECT u FROM Users u WHERE u.userEmail = :userEmail")
    , @NamedQuery(name = "Users.findByUserFirstname", query = "SELECT u FROM Users u WHERE u.userFirstname = :userFirstname")
    , @NamedQuery(name = "Users.findByUserLastname", query = "SELECT u FROM Users u WHERE u.userLastname = :userLastname")
    , @NamedQuery(name = "Users.findByUserMiddlename", query = "SELECT u FROM Users u WHERE u.userMiddlename = :userMiddlename")
    , @NamedQuery(name = "Users.findByUserEnabled", query = "SELECT u FROM Users u WHERE u.userEnabled = :userEnabled")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Lob
    @Column(name = "user_userid")
    private Object userUserid;
    @Basic(optional = false)
    @Column(name = "user_username")
    private String userUsername;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "user_email")
    private String userEmail;
    @Column(name = "user_firstname")
    private String userFirstname;
    @Column(name = "user_lastname")
    private String userLastname;
    @Column(name = "user_middlename")
    private String userMiddlename;
    @Basic(optional = false)
    @Column(name = "user_enabled")
    private boolean userEnabled;

    public Users() {
    }

    public Users(Object userUserid) {
        this.userUserid = userUserid;
    }

    public Users(Object userUserid, String userUsername, String userPassword, String userEmail, boolean userEnabled) {
        this.userUserid = userUserid;
        this.userUsername = userUsername;
        this.userPassword = userPassword;
        this.userEmail = userEmail;
        this.userEnabled = userEnabled;
    }

    public Object getUserUserid() {
        return userUserid;
    }

    public void setUserUserid(Object userUserid) {
        this.userUserid = userUserid;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserFirstname() {
        return userFirstname;
    }

    public void setUserFirstname(String userFirstname) {
        this.userFirstname = userFirstname;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserMiddlename() {
        return userMiddlename;
    }

    public void setUserMiddlename(String userMiddlename) {
        this.userMiddlename = userMiddlename;
    }

    public boolean getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userUserid != null ? userUserid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userUserid == null && other.userUserid != null) || (this.userUserid != null && !this.userUserid.equals(other.userUserid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.Users[ userUserid=" + userUserid + " ]";
    }
    
}
