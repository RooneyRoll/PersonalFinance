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
    , @NamedQuery(name = "Users.findByUserUuid", query = "SELECT u FROM Users u WHERE u.userUuid = :userUuid")
    , @NamedQuery(name = "Users.findByUserFirstname", query = "SELECT u FROM Users u WHERE u.userFirstname = :userFirstname")
    , @NamedQuery(name = "Users.findByUserLastname", query = "SELECT u FROM Users u WHERE u.userLastname = :userLastname")
    , @NamedQuery(name = "Users.findByUserPassword", query = "SELECT u FROM Users u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "Users.findByUserSurname", query = "SELECT u FROM Users u WHERE u.userSurname = :userSurname")
    , @NamedQuery(name = "Users.findByUserUsername", query = "SELECT u FROM Users u WHERE u.userUsername = :userUsername")})
public class Users implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "user_uuid")
    private Long userUuid;
    @Column(name = "user_firstname")
    private String userFirstname;
    @Column(name = "user_lastname")
    private String userLastname;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Column(name = "user_surname")
    private String userSurname;
    @Basic(optional = false)
    @Column(name = "user_username")
    private String userUsername;

    public Users() {
    }

    public Users(Long userUuid) {
        this.userUuid = userUuid;
    }

    public Users(Long userUuid, String userPassword, String userUsername) {
        this.userUuid = userUuid;
        this.userPassword = userPassword;
        this.userUsername = userUsername;
    }

    public Long getUserUuid() {
        return userUuid;
    }

    public void setUserUuid(Long userUuid) {
        this.userUuid = userUuid;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userUuid != null ? userUuid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Users)) {
            return false;
        }
        Users other = (Users) object;
        if ((this.userUuid == null && other.userUuid != null) || (this.userUuid != null && !this.userUuid.equals(other.userUuid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanagerdatapostgres.entities.Users[ userUuid=" + userUuid + " ]";
    }
    
}
