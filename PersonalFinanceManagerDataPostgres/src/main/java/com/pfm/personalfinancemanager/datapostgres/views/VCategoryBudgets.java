/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.views;

import java.io.Serializable;
import java.util.Date;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "v_category_budgets")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VCategoryBudgets.findAll", query = "SELECT v FROM VCategoryBudgets v")
    , @NamedQuery(name = "VCategoryBudgets.findByAmount", query = "SELECT v FROM VCategoryBudgets v WHERE v.amount = :amount")
    , @NamedQuery(name = "VCategoryBudgets.findByFromDate", query = "SELECT v FROM VCategoryBudgets v WHERE v.fromDate = :fromDate")
    , @NamedQuery(name = "VCategoryBudgets.findByToDate", query = "SELECT v FROM VCategoryBudgets v WHERE v.toDate = :toDate")
    , @NamedQuery(name = "VCategoryBudgets.findByCname", query = "SELECT v FROM VCategoryBudgets v WHERE v.cname = :cname")})
public class VCategoryBudgets implements Serializable {

    private static final long serialVersionUID = 1L;
   
    @Column(name = "id")
    @Id
    private UUID id;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "amount")
    private double amount;
    @Column(name = "from_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fromDate;
    @Column(name = "to_date")
    @Temporal(TemporalType.TIMESTAMP)
    private Date toDate;
    @Column(name = "cname")
    private String cname;
    
    @Column(name = "user_id")
    private UUID userId;

    public VCategoryBudgets() {
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

    public String getCname() {
        return cname;
    }

    public void setCname(String cname) {
        this.cname = cname;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }
    
}
