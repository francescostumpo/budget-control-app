package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "users", schema = "budget_schema")
public class UserEntity extends BaseEntity{

        Long id;
        String username;
        String name;
        String surname;
        String email;
        OrganizationEntity organizationEntity;

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        public Long getId() {
                return id;
        }

        public void setId(Long id) {
                this.id = id;
        }

        @Column(name = "username")
        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        @Column(name = "name")
        public String getName() {
                return name;
        }

        public void setName(String name) {
                this.name = name;
        }

        @Column(name = "surname")
        public String getSurname() {
                return surname;
        }

        public void setSurname(String surname) {
                this.surname = surname;
        }

        @Column(name = "email")
        public String getEmail() {
                return email;
        }

        public void setEmail(String email) {
                this.email = email;
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



