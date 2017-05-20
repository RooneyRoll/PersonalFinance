/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Misho
 */
@Entity
@Table(name = "user_budgets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "UserBudgets.findAll", query = "SELECT u FROM UserBudgets u")
    , @NamedQuery(name = "UserBudgets.findByUbFrom", query = "SELECT u FROM UserBudgets u WHERE u.ubFrom = :ubFrom")
    , @NamedQuery(name = "UserBudgets.findByUbTo", query = "SELECT u FROM UserBudgets u WHERE u.ubTo = :ubTo")})
public class UserBudgets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ub_id")
    private UUID ubId;
    @Basic(optional = false)
    @Column(name = "ub_from")
    @Temporal(TemporalType.DATE)
    private Date ubFrom;
    @Basic(optional = false)
    @Column(name = "ub_to")
    @Temporal(TemporalType.DATE)
    private Date ubTo;
    @JoinColumn(name = "ub_user", referencedColumnName = "user_userid")
    @ManyToOne(optional = false)
    private Users ubUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "cbBudget")
    private List<CategoryBudgets> categoryBudgetsList;

    public UserBudgets() {
    }

    public UserBudgets(UUID ubId) {
        this.ubId = ubId;
    }

    public UserBudgets(UUID ubId, Date ubFrom, Date ubTo) {
        this.ubId = ubId;
        this.ubFrom = ubFrom;
        this.ubTo = ubTo;
    }

    public UUID getUbId() {
        return ubId;
    }

    public void setUbId(UUID ubId) {
        this.ubId = ubId;
    }

    public Date getUbFrom() {
        return ubFrom;
    }

    public void setUbFrom(Date ubFrom) {
        this.ubFrom = ubFrom;
    }

    public Date getUbTo() {
        return ubTo;
    }

    public void setUbTo(Date ubTo) {
        this.ubTo = ubTo;
    }

    public Users getUbUser() {
        return ubUser;
    }

    public void setUbUser(Users ubUser) {
        this.ubUser = ubUser;
    }

    @XmlTransient
    public List<CategoryBudgets> getCategoryBudgetsList() {
        return categoryBudgetsList;
    }

    public void setCategoryBudgetsList(List<CategoryBudgets> categoryBudgetsList) {
        this.categoryBudgetsList = categoryBudgetsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ubId != null ? ubId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof UserBudgets)) {
            return false;
        }
        UserBudgets other = (UserBudgets) object;
        if ((this.ubId == null && other.ubId != null) || (this.ubId != null && !this.ubId.equals(other.ubId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.UserBudgets[ ubId=" + ubId + " ]";
    }
    
}
