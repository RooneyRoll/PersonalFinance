/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author mihail
 */
@Entity
@Table(name = "payments")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Payments.findAll", query = "SELECT p FROM Payments p")
    , @NamedQuery(name = "Payments.findByPAmount", query = "SELECT p FROM Payments p WHERE p.pAmount = :pAmount")
    , @NamedQuery(name = "Payments.findByPDate", query = "SELECT p FROM Payments p WHERE p.pDate = :pDate")
    , @NamedQuery(name = "Payments.findByPDescription", query = "SELECT p FROM Payments p WHERE p.pDescription = :pDescription")
    , @NamedQuery(name = "Payments.findByPActive", query = "SELECT p FROM Payments p WHERE p.pActive = :pActive")
    , @NamedQuery(name = "Payments.findByPCoveredRecurringPeriods", query = "SELECT p FROM Payments p WHERE p.pCoveredRecurringPeriods = :pCoveredRecurringPeriods")})
public class Payments implements Serializable {

    @JoinColumn(name = "p_category", referencedColumnName = "pcat_id")
    @ManyToOne(optional = false)
    private PaymentCategories pCategory;
    @JoinColumn(name = "p_recurring_budget_payment", referencedColumnName = "rbp_id")
    @ManyToOne
    private RecurringBudgetPayments pRecurringBudgetPayment;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "p_id")
    private UUID pId;
    @Basic(optional = false)
    @Column(name = "p_amount")
    private double pAmount;
    @Basic(optional = false)
    @Column(name = "p_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date pDate;
    @Column(name = "p_description")
    private String pDescription;
    @Basic(optional = false)
    @Column(name = "p_active")
    private boolean pActive;
    @Column(name = "p_covered_recurring_periods")
    private Integer pCoveredRecurringPeriods;

    public Payments() {
    }

    public Payments(UUID pId) {
        this.pId = pId;
    }

    public Payments(UUID pId, double pAmount, Date pDate, boolean pActive) {
        this.pId = pId;
        this.pAmount = pAmount;
        this.pDate = pDate;
        this.pActive = pActive;
    }

    public UUID getPId() {
        return pId;
    }

    public void setPId(UUID pId) {
        this.pId = pId;
    }

    public double getPAmount() {
        return pAmount;
    }

    public void setPAmount(double pAmount) {
        this.pAmount = pAmount;
    }

    public Date getPDate() {
        return pDate;
    }

    public void setPDate(Date pDate) {
        this.pDate = pDate;
    }

    public String getPDescription() {
        return pDescription;
    }

    public void setPDescription(String pDescription) {
        this.pDescription = pDescription;
    }

    public boolean getPActive() {
        return pActive;
    }

    public void setPActive(boolean pActive) {
        this.pActive = pActive;
    }

    public Integer getPCoveredRecurringPeriods() {
        return pCoveredRecurringPeriods;
    }

    public void setPCoveredRecurringPeriods(Integer pCoveredRecurringPeriods) {
        this.pCoveredRecurringPeriods = pCoveredRecurringPeriods;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pId != null ? pId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Payments)) {
            return false;
        }
        Payments other = (Payments) object;
        if ((this.pId == null && other.pId != null) || (this.pId != null && !this.pId.equals(other.pId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.Payments[ pId=" + pId + " ]";
    }

    public PaymentCategories getPCategory() {
        return pCategory;
    }

    public void setPCategory(PaymentCategories pCategory) {
        this.pCategory = pCategory;
    }

    public RecurringBudgetPayments getPRecurringBudgetPayment() {
        return pRecurringBudgetPayment;
    }

    public void setPRecurringBudgetPayment(RecurringBudgetPayments pRecurringBudgetPayment) {
        this.pRecurringBudgetPayment = pRecurringBudgetPayment;
    }
    
}
