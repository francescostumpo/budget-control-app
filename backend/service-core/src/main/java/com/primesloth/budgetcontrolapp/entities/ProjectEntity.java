package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "projects", schema = "budget_schema")
public class ProjectEntity extends BaseEntity{

    Long id;
    String name;
    Double revenues;
    Double cci;
    LocalDate startDate;
    LocalDate endDate;
    ClientEntity clientEntity;


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

    @Column(name = "revenues")
    public Double getRevenues() {
        return revenues;
    }

    public void setRevenues(Double revenues) {
        this.revenues = revenues;
    }

    @Column(name = "cci")
    public Double getCci() {
        return cci;
    }

    public void setCci(Double cci) {
        this.cci = cci;
    }

    @Column(name = "start_date")
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    @Column(name = "end_date")
    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @OneToOne// Establish one-to-one relationship
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    public ClientEntity getClientEntity() {
        return clientEntity;
    }

    public void setClientEntity(ClientEntity clientEntity) {
        this.clientEntity = clientEntity;
    }

}
