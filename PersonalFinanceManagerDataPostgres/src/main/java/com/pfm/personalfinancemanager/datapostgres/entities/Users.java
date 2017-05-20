/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misho
 */
@Entity
@Table(name = "users")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Users.findAll", query = "SELECT u FROM Users u")
    , @NamedQuery(name = "Users.findByUserEmail", query = "SELECT u FROM Users u WHERE u.userEmail = :userEmail")
    , @NamedQuery(name = "Users.findByUserEnabled", query = "SELECT u FROM Users u WHERE u.userEnabled = :userEnabled")
    , @NamedQuery(name = "Users.findByUserFirstname", query = "SELECT u FROM Users u WHERE u.userFirstname = :userFirstname")
    , @NamedQuery(name = "Users.findByUserLastname", query = "SELECT u FROM Users u WHERE u.userLastname = :userLastname")
    , @NamedQuery(name = "Users.findByUserMiddlename", query = "SELECT u FROM Users u WHERE u.userMiddlename = :userMiddlename")
    , @NamedQuery(name = "Users.findByUserPassword", query = "SELECT u FROM Users u WHERE u.userPassword = :userPassword")
    , @NamedQuery(name = "Users.findByUserUsername", query = "SELECT u FROM Users u WHERE u.userUsername = :userUsername")})
public class Users implements Serializable {

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ubUser")
    private List<UserBudgets> userBudgetsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ptypeUser")
    private List<PaymentTypes> paymentTypesList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_userid")
    private UUID userUserid;
    @Column(name = "user_email")
    private String userEmail;
    @Basic(optional = false)
    @Column(name = "user_enabled")
    private boolean userEnabled;
    @Column(name = "user_firstname")
    private String userFirstname;
    @Column(name = "user_lastname")
    private String userLastname;
    @Column(name = "user_middlename")
    private String userMiddlename;
    @Basic(optional = false)
    @Column(name = "user_password")
    private String userPassword;
    @Basic(optional = false)
    @Column(name = "user_username")
    private String userUsername;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcatUser")
    private List<PaymentCategories> paymentCategoriesList;

    public Users() {
    }

    public Users(UUID userUserid) {
        this.userUserid = userUserid;
    }

    public Users(UUID userUserid, boolean userEnabled, String userPassword, String userUsername) {
        this.userUserid = userUserid;
        this.userEnabled = userEnabled;
        this.userPassword = userPassword;
        this.userUsername = userUsername;
    }

    public UUID getUserUserid() {
        return userUserid;
    }

    public void setUserUserid(UUID userUserid) {
        this.userUserid = userUserid;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public boolean getUserEnabled() {
        return userEnabled;
    }

    public void setUserEnabled(boolean userEnabled) {
        this.userEnabled = userEnabled;
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

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserUsername() {
        return userUsername;
    }

    public void setUserUsername(String userUsername) {
        this.userUsername = userUsername;
    }

    @XmlTransient
    public List<PaymentCategories> getPaymentCategoriesList() {
        return paymentCategoriesList;
    }

    public void setPaymentCategoriesList(List<PaymentCategories> paymentCategoriesList) {
        this.paymentCategoriesList = paymentCategoriesList;
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

    @XmlTransient
    public List<UserBudgets> getUserBudgetsList() {
        return userBudgetsList;
    }

    public void setUserBudgetsList(List<UserBudgets> userBudgetsList) {
        this.userBudgetsList = userBudgetsList;
    }

    @XmlTransient
    public List<PaymentTypes> getPaymentTypesList() {
        return paymentTypesList;
    }

    public void setPaymentTypesList(List<PaymentTypes> paymentTypesList) {
        this.paymentTypesList = paymentTypesList;
    }
}
