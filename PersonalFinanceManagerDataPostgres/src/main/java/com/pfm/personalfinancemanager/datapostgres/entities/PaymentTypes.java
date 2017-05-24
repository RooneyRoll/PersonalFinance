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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "payment_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentTypes.findAll", query = "SELECT p FROM PaymentTypes p")
    , @NamedQuery(name = "PaymentTypes.findByPtypeName", query = "SELECT p FROM PaymentTypes p WHERE p.ptypeName = :ptypeName")
    , @NamedQuery(name = "PaymentTypes.findByPtypeDescription", query = "SELECT p FROM PaymentTypes p WHERE p.ptypeDescription = :ptypeDescription")
    , @NamedQuery(name = "PaymentTypes.findByPtypeActive", query = "SELECT p FROM PaymentTypes p WHERE p.ptypeActive = :ptypeActive")})
public class PaymentTypes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "ptype_id")
    private UUID ptypeId;
    @Basic(optional = false)
    @Column(name = "ptype_name")
    private String ptypeName;
    @Column(name = "ptype_description")
    private String ptypeDescription;
    @Basic(optional = false)
    @Column(name = "ptype_active")
    private boolean ptypeActive;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pcatType")
    private List<PaymentCategories> paymentCategoriesList;
    @JoinColumn(name = "ptype_user", referencedColumnName = "user_userid")
    @ManyToOne(optional = false)
    private Users ptypeUser;

    public PaymentTypes() {
    }

    public PaymentTypes(UUID ptypeId) {
        this.ptypeId = ptypeId;
    }

    public PaymentTypes(UUID ptypeId, String ptypeName, boolean ptypeActive) {
        this.ptypeId = ptypeId;
        this.ptypeName = ptypeName;
        this.ptypeActive = ptypeActive;
    }

    public UUID getPtypeId() {
        return ptypeId;
    }

    public void setPtypeId(UUID ptypeId) {
        this.ptypeId = ptypeId;
    }

    public String getPtypeName() {
        return ptypeName;
    }

    public void setPtypeName(String ptypeName) {
        this.ptypeName = ptypeName;
    }

    public String getPtypeDescription() {
        return ptypeDescription;
    }

    public void setPtypeDescription(String ptypeDescription) {
        this.ptypeDescription = ptypeDescription;
    }

    public boolean getPtypeActive() {
        return ptypeActive;
    }

    public void setPtypeActive(boolean ptypeActive) {
        this.ptypeActive = ptypeActive;
    }

    @XmlTransient
    public List<PaymentCategories> getPaymentCategoriesList() {
        return paymentCategoriesList;
    }

    public void setPaymentCategoriesList(List<PaymentCategories> paymentCategoriesList) {
        this.paymentCategoriesList = paymentCategoriesList;
    }

    public Users getPtypeUser() {
        return ptypeUser;
    }

    public void setPtypeUser(Users ptypeUser) {
        this.ptypeUser = ptypeUser;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (ptypeId != null ? ptypeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentTypes)) {
            return false;
        }
        PaymentTypes other = (PaymentTypes) object;
        if ((this.ptypeId == null && other.ptypeId != null) || (this.ptypeId != null && !this.ptypeId.equals(other.ptypeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.PaymentTypes[ ptypeId=" + ptypeId + " ]";
    }
    
}
