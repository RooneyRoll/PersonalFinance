/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.List;
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
 * @author mihail
=======
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misho
>>>>>>> origin/master
 */
@Entity
@Table(name = "recurring_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecurringTypes.findAll", query = "SELECT r FROM RecurringTypes r")
    , @NamedQuery(name = "RecurringTypes.findByRtId", query = "SELECT r FROM RecurringTypes r WHERE r.rtId = :rtId")
    , @NamedQuery(name = "RecurringTypes.findByRtName", query = "SELECT r FROM RecurringTypes r WHERE r.rtName = :rtName")})
public class RecurringTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "rt_id")
    private Integer rtId;
    @Basic(optional = false)
    @Column(name = "rt_name")
    private String rtName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rbpRecType")
    private List<RecurringBudgetPayments> recurringBudgetPaymentsList;

    public RecurringTypes() {
    }

    public RecurringTypes(Integer rtId) {
        this.rtId = rtId;
    }

    public RecurringTypes(Integer rtId, String rtName) {
        this.rtId = rtId;
        this.rtName = rtName;
    }

    public Integer getRtId() {
        return rtId;
    }

    public void setRtId(Integer rtId) {
        this.rtId = rtId;
    }

    public String getRtName() {
        return rtName;
    }

    public void setRtName(String rtName) {
        this.rtName = rtName;
    }

    @XmlTransient
    public List<RecurringBudgetPayments> getRecurringBudgetPaymentsList() {
        return recurringBudgetPaymentsList;
    }

    public void setRecurringBudgetPaymentsList(List<RecurringBudgetPayments> recurringBudgetPaymentsList) {
        this.recurringBudgetPaymentsList = recurringBudgetPaymentsList;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rtId != null ? rtId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecurringTypes)) {
            return false;
        }
        RecurringTypes other = (RecurringTypes) object;
        if ((this.rtId == null && other.rtId != null) || (this.rtId != null && !this.rtId.equals(other.rtId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.RecurringTypes[ rtId=" + rtId + " ]";
    }
    
}
