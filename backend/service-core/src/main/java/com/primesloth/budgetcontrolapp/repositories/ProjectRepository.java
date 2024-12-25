package com.primesloth.budgetcontrolapp.repositories;

import com.primesloth.budgetcontrolapp.entities.ProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEntity, Long> {

    @Query("select pe from ProjectEntity pe where pe.clientEntity.id = :id")
    List<ProjectEntity> findAllByClientId(@Param("id") long id);
}
