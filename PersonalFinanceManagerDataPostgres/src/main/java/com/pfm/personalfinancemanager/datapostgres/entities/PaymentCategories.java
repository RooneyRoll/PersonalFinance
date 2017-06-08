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
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author mihail
 */
@Entity
@Table(name = "payment_categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentCategories.findAll", query = "SELECT p FROM PaymentCategories p")
    , @NamedQuery(name = "PaymentCategories.findByPcatName", query = "SELECT p FROM PaymentCategories p WHERE p.pcatName = :pcatName")
    , @NamedQuery(name = "PaymentCategories.findByPcatActive", query = "SELECT p FROM PaymentCategories p WHERE p.pcatActive = :pcatActive")
    , @NamedQuery(name = "PaymentCategories.findByPcatDescription", query = "SELECT p FROM PaymentCategories p WHERE p.pcatDescription = :pcatDescription")})
public class PaymentCategories implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "pcat_id")
    private UUID pcatId;
    @Basic(optional = false)
    @Column(name = "pcat_name")
    private String pcatName;
    @Basic(optional = false)
    @Column(name = "pcat_active")
    private boolean pcatActive;
    @Column(name = "pcat_description")
    private String pcatDescription;
    @JoinColumn(name = "pcat_type", referencedColumnName = "ptype_id")
    @ManyToOne(optional = false)
    private PaymentTypes pcatType;
    @JoinColumn(name = "pcat_user", referencedColumnName = "user_userid")
    @ManyToOne(optional = false)
    private Users pcatUser;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pCategory")
    private List<Payments> paymentsList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rbpCategory")
    private List<RecurringBudgetPayments> recurringBudgetPaymentsList;

    public PaymentCategories() {
    }

    public PaymentCategories(UUID pcatId) {
        this.pcatId = pcatId;
    }

    public PaymentCategories(UUID pcatId, String pcatName, boolean pcatActive) {
        this.pcatId = pcatId;
        this.pcatName = pcatName;
        this.pcatActive = pcatActive;
    }

    public UUID getPcatId() {
        return pcatId;
    }

    public void setPcatId(UUID pcatId) {
        this.pcatId = pcatId;
    }

    public String getPcatName() {
        return pcatName;
    }

    public void setPcatName(String pcatName) {
        this.pcatName = pcatName;
    }

    public boolean getPcatActive() {
        return pcatActive;
    }

    public void setPcatActive(boolean pcatActive) {
        this.pcatActive = pcatActive;
    }

    public String getPcatDescription() {
        return pcatDescription;
    }

    public void setPcatDescription(String pcatDescription) {
        this.pcatDescription = pcatDescription;
    }

    public PaymentTypes getPcatType() {
        return pcatType;
    }

    public void setPcatType(PaymentTypes pcatType) {
        this.pcatType = pcatType;
    }

    public Users getPcatUser() {
        return pcatUser;
    }

    public void setPcatUser(Users pcatUser) {
        this.pcatUser = pcatUser;
    }

    @XmlTransient
    public List<Payments> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
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
        hash += (pcatId != null ? pcatId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PaymentCategories)) {
            return false;
        }
        PaymentCategories other = (PaymentCategories) object;
        if ((this.pcatId == null && other.pcatId != null) || (this.pcatId != null && !this.pcatId.equals(other.pcatId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.PaymentCategories[ pcatId=" + pcatId + " ]";
    }

}
