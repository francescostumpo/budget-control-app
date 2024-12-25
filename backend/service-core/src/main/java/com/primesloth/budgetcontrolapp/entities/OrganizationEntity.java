package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;


@Entity
@Table(name = "organizations", schema = "budget_schema")
public class OrganizationEntity extends BaseEntity{

        Long Id;
        String name;
        String email;
        LocalDateTime endPaymentDate; //TODO To-be implemented in a future release
        Boolean endActiveDate;
        List<UserEntity> userEntities;
        List<ResourceEntity> resourceEntityList;


        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long getId() {
                return Id;
        }

        public void setId(Long id) {
                Id = id;
        }

        @Column(name = "name")
        public String getName() {
                return name;
        }

        public void setName(String businessName) {
                this.name = businessName;
        }

        @Column(name = "email")
        public String getEmail() {
                return email;
        }

        public void setEmail(String businessEmail) {
                this.email = businessEmail;
        }

        @Column(name = "end_payment_date")
        public LocalDateTime getEndPaymentDate() {
                return endPaymentDate;
        }

        public void setEndPaymentDate(LocalDateTime endPaymentDate) {
                this.endPaymentDate = endPaymentDate;
        }

        @Column(name = "end_active_date")
        public Boolean getEndActiveDate() {
                return endActiveDate;
        }

        public void setEndActiveDate(Boolean endActiveDate) {
                this.endActiveDate = endActiveDate;
        }

        @OneToMany(mappedBy = "organizationEntity")
        public List<UserEntity> getUserEntities() {
                return userEntities;
        }

        public void setUserEntities(List<UserEntity> userEntities) {
                this.userEntities = userEntities;
        }

        @OneToMany(mappedBy = "organizationEntity")
        public List<ResourceEntity> getResourceEntityList() {
                return resourceEntityList;
        }

        public void setResourceEntityList(List<ResourceEntity> resourceEntityList) {
                this.resourceEntityList = resourceEntityList;
        }
}
