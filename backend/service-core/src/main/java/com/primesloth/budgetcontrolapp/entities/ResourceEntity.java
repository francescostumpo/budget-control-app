package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "resources", schema = "budget_schema")
public class ResourceEntity extends BaseEntity{

    private Long id;
    private String username;
    private Level level;
    private Double lcr;
    private Double chargeability;
    private OrganizationEntity organizationEntity;

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

    @Column(name = "level")
    public Level getLevel() {
        return level;
    }

    public void setLevel(Level level) {
        this.level = level;
    }

    @Column(name = "lcr")
    public Double getLcr() {
        return lcr;
    }

    public void setLcr(Double lcr) {
        this.lcr = lcr;
    }

    @Column(name = "chargeability")
    public Double getChargeability() {
        return chargeability;
    }

    public void setChargeability(Double chargeability) {
        this.chargeability = chargeability;
    }

    @ManyToOne
    @JoinColumn(name = "organization_id", referencedColumnName = "id")
    public OrganizationEntity getOrganizationEntity() {
        return organizationEntity;
    }

    public void setOrganizationEntity(OrganizationEntity organizationEntity) {
        this.organizationEntity = organizationEntity;
    }

    @Getter
    public enum Level {
        ANALYST("Analyst"),
        CONSULTANT("Consultant"),
        MANAGER("Manager"),
        SENIOR_MANAGER("Senior Manager"),
        MANAGING_DIRECTOR("Managing Director");

        private final String value;

        Level(String value) {
            this.value = value;
        }

    }
}
