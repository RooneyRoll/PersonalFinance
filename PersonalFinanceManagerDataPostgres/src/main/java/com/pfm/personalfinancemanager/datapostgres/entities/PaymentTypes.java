/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "payment_types")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentTypes.findAll", query = "SELECT p FROM PaymentTypes p")
    , @NamedQuery(name = "PaymentTypes.findByPtypeName", query = "SELECT p FROM PaymentTypes p WHERE p.ptypeName = :ptypeName")
    , @NamedQuery(name = "PaymentTypes.findByPtypeDescription", query = "SELECT p FROM PaymentTypes p WHERE p.ptypeDescription = :ptypeDescription")})
public class PaymentTypes implements Serializable {

    @Basic(optional = false)
    @Column(name = "ptype_active")
    private boolean ptypeActive;
    @Column(name = "ptype_user")
    private UUID ptypeUser;
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
    public PaymentTypes() {
    }

    public PaymentTypes(UUID ptypeId) {
        this.ptypeId = ptypeId;
    }

    public PaymentTypes(UUID ptypeId, String ptypeName) {
        this.ptypeId = ptypeId;
        this.ptypeName = ptypeName;
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

    public boolean getPtypeActive() {
        return ptypeActive;
    }

    public void setPtypeActive(boolean ptypeActive) {
        this.ptypeActive = ptypeActive;
    }

    public UUID getPtypeUser() {
        return ptypeUser;
    }

    public void setPtypeUser(UUID ptypeUser) {
        this.ptypeUser = ptypeUser;
    }
    
}
