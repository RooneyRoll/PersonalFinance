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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Misho
 */
@Entity
@Table(name = "category_budgets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoryBudgets.findAll", query = "SELECT c FROM CategoryBudgets c")
    , @NamedQuery(name = "CategoryBudgets.findByCbAmount", query = "SELECT c FROM CategoryBudgets c WHERE c.cbAmount = :cbAmount")
    , @NamedQuery(name = "CategoryBudgets.findByCbActive", query = "SELECT c FROM CategoryBudgets c WHERE c.cbActive = :cbActive")})
public class CategoryBudgets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "cb_id")
    private UUID cbId;
    @Basic(optional = false)
    @Column(name = "cb_amount")
    private double cbAmount;
    @Basic(optional = false)
    @Column(name = "cb_active")
    private boolean cbActive;
    @JoinColumn(name = "cb_category_id", referencedColumnName = "pcat_id")
    @ManyToOne(optional = false)
    private PaymentCategories cbCategoryId;
    @JoinColumn(name = "cb_budget", referencedColumnName = "ub_id")
    @ManyToOne(optional = false)
    private UserBudgets cbBudget;

    public CategoryBudgets() {
    }

    public CategoryBudgets(UUID cbId) {
        this.cbId = cbId;
    }

    public CategoryBudgets(UUID cbId, double cbAmount, boolean cbActive) {
        this.cbId = cbId;
        this.cbAmount = cbAmount;
        this.cbActive = cbActive;
    }

    public UUID getCbId() {
        return cbId;
    }

    public void setCbId(UUID cbId) {
        this.cbId = cbId;
    }

    public double getCbAmount() {
        return cbAmount;
    }

    public void setCbAmount(double cbAmount) {
        this.cbAmount = cbAmount;
    }

    public boolean getCbActive() {
        return cbActive;
    }

    public void setCbActive(boolean cbActive) {
        this.cbActive = cbActive;
    }

    public PaymentCategories getCbCategoryId() {
        return cbCategoryId;
    }

    public void setCbCategoryId(PaymentCategories cbCategoryId) {
        this.cbCategoryId = cbCategoryId;
    }

    public UserBudgets getCbBudget() {
        return cbBudget;
    }

    public void setCbBudget(UserBudgets cbBudget) {
        this.cbBudget = cbBudget;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cbId != null ? cbId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryBudgets)) {
            return false;
        }
        CategoryBudgets other = (CategoryBudgets) object;
        if ((this.cbId == null && other.cbId != null) || (this.cbId != null && !this.cbId.equals(other.cbId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.CategoryBudgets[ cbId=" + cbId + " ]";
    }
    
}
