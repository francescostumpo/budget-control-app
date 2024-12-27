package com.primesloth.budgetcontrolapp.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "resources_projects", schema = "budget_schema")
public class ResourceProjectEntity extends BaseEntity{

    Long id;
    ResourceEntity resourceEntity;
    ProjectEntity projectEntity;


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @ManyToOne
    @JoinColumn(name = "resource_id", referencedColumnName = "id")
    public ResourceEntity getResourceEntity() {
        return resourceEntity;
    }

    public void setResourceEntity(ResourceEntity resourceEntity) {
        this.resourceEntity = resourceEntity;
    }

    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    public ProjectEntity getProjectEntity() {
        return projectEntity;
    }

    public void setProjectEntity(ProjectEntity projectEntity) {
        this.projectEntity = projectEntity;
    }
}
