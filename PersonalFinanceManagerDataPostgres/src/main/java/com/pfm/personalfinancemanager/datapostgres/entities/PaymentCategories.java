/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.pfm.personalfinancemanager.datapostgres.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Admin
 */
@Entity
@Table(name = "payment_categories")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PaymentCategorys.findAll", query = "SELECT u FROM PaymentCategorys u")
    , @NamedQuery(name = "PaymentCategorys.findById", query = "SELECT u FROM PaymentCategorys u WHERE u.id = :id")
    , @NamedQuery(name = "PaymentCategorys.findByCategoryName", query = "SELECT u FROM PaymentCategorys u WHERE u.name = :categoryName")})
public class PaymentCategories implements Serializable {

    @Id
    @Basic(optional = false)
    @SequenceGenerator(name = "my_seq", sequenceName = "payment_categories_gen_id", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "name")
    private String name;

    @Basic(optional = false)
    @Column(name = "active")
    private boolean active;

    public PaymentCategories() {
    }

    public PaymentCategories(Integer id, String name, boolean active) {
        this.id = id;
        this.name = name;
        this.active = active;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.id);
        hash = 83 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PaymentCategories other = (PaymentCategories) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PaymentCategorys{" + "id=" + id + ", name=" + name + '}';
    }

}
