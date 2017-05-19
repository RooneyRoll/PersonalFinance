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
@Table(name = "category_budget")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CategoryBudgets.findAll", query = "SELECT c FROM CategoryBudgets c")
    , @NamedQuery(name = "CategoryBudgets.findByAmount", query = "SELECT c FROM CategoryBudgets c WHERE c.amount = :amount")
    , @NamedQuery(name = "CategoryBudgets.findByFromDate", query = "SELECT c FROM CategoryBudgets c WHERE c.fromDate = :fromDate")
    , @NamedQuery(name = "CategoryBudgets.findByToDate", query = "SELECT c FROM CategoryBudgets c WHERE c.toDate = :toDate")})
public class CategoryBudgets implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;
    @Basic(optional = false)
    @Column(name = "amount")
    private double amount;
    @Column(name = "from_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @JoinColumn(name = "category_id", referencedColumnName = "pcat_id")
    @ManyToOne(optional = false)
    private PaymentCategories categoryId;

    public CategoryBudgets() {
    }

    public CategoryBudgets(UUID id) {
        this.id = id;
    }

    public CategoryBudgets(UUID id, double amount) {
        this.id = id;
        this.amount = amount;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public Date getFromDate() {
        return fromDate;
    }

    public void setFromDate(Date fromDate) {
        this.fromDate = fromDate;
    }

    public Date getToDate() {
        return toDate;
    }

    public void setToDate(Date toDate) {
        this.toDate = toDate;
    }

    public PaymentCategories getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(PaymentCategories categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CategoryBudgets)) {
            return false;
        }
        CategoryBudgets other = (CategoryBudgets) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.pfm.personalfinancemanager.datapostgres.entities.CategoryBudgets[ id=" + id + " ]";
    }
    
}
