package com.primesloth.budgetcontrolapp.repositories;

import com.primesloth.budgetcontrolapp.entities.ResourceProjectEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResourceProjectRepository extends JpaRepository<ResourceProjectEntity, Long> {

    @Query("select rp from ResourceProjectEntity rp where rp.resourceEntity.id = :resourceId and rp.projectEntity.id = :projectId")
    Optional<ResourceProjectEntity> findByResourceIdAndProjectId(@Param("resourceId") Long resourceId, @Param("projectId") Long projectId);

    @Modifying
    @Query("delete from ResourceProjectEntity rp where rp.projectEntity.id = :projectId")
    void deleteAllByProjectId(@Param("projectId") Long projectId);
}
