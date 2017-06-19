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
 * @author mihail
 */
@Entity
@Table(name = "recurring_budget_payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RecurringBudgetPayments.findAll", query = "SELECT r FROM RecurringBudgetPayments r")
    , @NamedQuery(name = "RecurringBudgetPayments.findByRbpPeriods", query = "SELECT r FROM RecurringBudgetPayments r WHERE r.rbpPeriods = :rbpPeriods")
    , @NamedQuery(name = "RecurringBudgetPayments.findByRbpActive", query = "SELECT r FROM RecurringBudgetPayments r WHERE r.rbpActive = :rbpActive")
    , @NamedQuery(name = "RecurringBudgetPayments.findByRbpDateStart", query = "SELECT r FROM RecurringBudgetPayments r WHERE r.rbpDateStart = :rbpDateStart")
    , @NamedQuery(name = "RecurringBudgetPayments.findByRbpAmount", query = "SELECT r FROM RecurringBudgetPayments r WHERE r.rbpAmount = :rbpAmount")})
public class RecurringBudgetPayments implements Serializable {

   
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "rbp_id")
    private UUID rbpId;
    @Basic(optional = false)
    @Column(name = "rbp_periods")
    private int rbpPeriods;
    @Basic(optional = false)
    @Column(name = "rbp_active")
    private boolean rbpActive;
    @Basic(optional = false)
    @Column(name = "rbp_date_start")
    @Temporal(TemporalType.TIMESTAMP)
    private Date rbpDateStart;
    @Basic(optional = false)
    @Column(name = "rbp_amount")
    private double rbpAmount;
    @OneToMany(mappedBy = "pRecurringBudgetPayment")
    private List<Payments> paymentsList;
    @JoinColumn(name = "rbp_category", referencedColumnName = "pcat_id")
    @ManyToOne(optional = false)
    private PaymentCategories rbpCategory;
    @JoinColumn(name = "rbp_rec_type", referencedColumnName = "rt_id")
    @ManyToOne(optional = false)
    private RecurringTypes rbpRecType;
    @JoinColumn(name = "rbp_user", referencedColumnName = "user_userid")
    @ManyToOne(optional = false)
    private Users rbpUser;
    @Basic(optional = false)
    @Column(name = "rbv_finished")
    private boolean rbvFinished;
    @Column(name = "rbp_finished_date")
    @Temporal(TemporalType.TIME)
    private Date rbpFinishedDate;
    @Column(name = "rbp_title")
    private String rbpTitle;
    @Column(name = "rbp_description")
    private String rbpDescription;
    @Basic(optional = false)
    @Column(name = "rbp_miss_per_periods")
    private int rbpMissPerPeriods;

    
    
    public RecurringBudgetPayments() {
    }

    public RecurringBudgetPayments(UUID rbpId) {
        this.rbpId = rbpId;
    }

    public RecurringBudgetPayments(UUID rbpId, int rbpPeriods, boolean rbpActive, Date rbpDateStart, double rbpAmount) {
        this.rbpId = rbpId;
        this.rbpPeriods = rbpPeriods;
        this.rbpActive = rbpActive;
        this.rbpDateStart = rbpDateStart;
        this.rbpAmount = rbpAmount;
    }

    public UUID getRbpId() {
        return rbpId;
    }

    public void setRbpId(UUID rbpId) {
        this.rbpId = rbpId;
    }

    public int getRbpPeriods() {
        return rbpPeriods;
    }

    public void setRbpPeriods(int rbpPeriods) {
        this.rbpPeriods = rbpPeriods;
    }

    public boolean getRbpActive() {
        return rbpActive;
    }

    public void setRbpActive(boolean rbpActive) {
        this.rbpActive = rbpActive;
    }

    public Date getRbpDateStart() {
        return rbpDateStart;
    }

    public void setRbpDateStart(Date rbpDateStart) {
        this.rbpDateStart = rbpDateStart;
    }

    public double getRbpAmount() {
        return rbpAmount;
    }

    public void setRbpAmount(double rbpAmount) {
        this.rbpAmount = rbpAmount;
    }

    @XmlTransient
    public List<Payments> getPaymentsList() {
        return paymentsList;
    }

    public void setPaymentsList(List<Payments> paymentsList) {
        this.paymentsList = paymentsList;
    }

    public PaymentCategories getRbpCategory() {
        return rbpCategory;
    }

    public void setRbpCategory(PaymentCategories rbpCategory) {
        this.rbpCategory = rbpCategory;
    }

    public RecurringTypes getRbpRecType() {
        return rbpRecType;
    }

    public void setRbpRecType(RecurringTypes rbpRecType) {
        this.rbpRecType = rbpRecType;
    }

    public Users getRbpUser() {
        return rbpUser;
    }

    public void setRbpUser(Users rbpUser) {
        this.rbpUser = rbpUser;
    }

    public String getRbpTitle() {
        return rbpTitle;
    }

    public void setRbpTitle(String rbpTitle) {
        this.rbpTitle = rbpTitle;
    }

    public String getRbpDescription() {
        return rbpDescription;
    }

    public void setRbpDescription(String rbpDescription) {
        this.rbpDescription = rbpDescription;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (rbpId != null ? rbpId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RecurringBudgetPayments)) {
            return false;
        }
        RecurringBudgetPayments other = (RecurringBudgetPayments) object;
        if ((this.rbpId == null && other.rbpId != null) || (this.rbpId != null && !this.rbpId.equals(other.rbpId))) {
            return false;
        }
        return true;
    }

    
    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.RecurringBudgetPayments[ rbpId=" + rbpId + " ]";
    }

    public boolean getRbvFinished() {
        return rbvFinished;
    }

    public void setRbvFinished(boolean rbvFinished) {
        this.rbvFinished = rbvFinished;
    }

    public Date getRbpFinishedDate() {
        return rbpFinishedDate;
    }

    public void setRbpFinishedDate(Date rbpFinishedDate) {
        this.rbpFinishedDate = rbpFinishedDate;
    }

    public int getRbpMissPerPeriods() {
        return rbpMissPerPeriods;
    }

    public void setRbpMissPerPeriods(int rbpMissPerPeriods) {
        this.rbpMissPerPeriods = rbpMissPerPeriods;
    }   
}
