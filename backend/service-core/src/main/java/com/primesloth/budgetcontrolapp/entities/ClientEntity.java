package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "clients", schema = "budget_schema")
public class ClientEntity extends BaseEntity{

    private Long id;
    private String name;

    private Double totalSold;

    private Double totalSaving;

    private OrganizationEntity organizationEntity;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "total_sold")
    public Double getTotalSold() {
        return totalSold;
    }

    public void setTotalSold(Double totalSold) {
        this.totalSold = totalSold;
    }

    @Column(name = "total_saving")
    public Double getTotalSaving() {
        return totalSaving;
    }

    public void setTotalSaving(Double totalSaving) {
        this.totalSaving = totalSaving;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    public OrganizationEntity getOrganizationEntity() {
        return organizationEntity;
    }

    public void setOrganizationEntity(OrganizationEntity organizationEntity) {
        this.organizationEntity = organizationEntity;
    }

   }
