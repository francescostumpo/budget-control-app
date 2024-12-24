package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@MappedSuperclass
public abstract class BaseEntity {

    LocalDateTime createdAt;
    LocalDateTime updatedAt;
    String createdBy;
    String updateBy;


    @Column(name = "created_at")
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    @Column(name = "updated_at")
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    @Column(name = "created_by")
    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Column(name = "updated_by")
    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    @PrePersist
    void onCreate(){
        this.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        //TODO created_by
    }

    @PreUpdate
    void onUpdate(){
        this.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        //TODO update_by
    }
}
