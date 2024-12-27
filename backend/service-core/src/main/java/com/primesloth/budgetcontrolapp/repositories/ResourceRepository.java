package com.primesloth.budgetcontrolapp.repositories;

import com.primesloth.budgetcontrolapp.entities.ResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResourceRepository extends JpaRepository<ResourceEntity, Long> {

    @Query("select re from ResourceEntity re where re.organizationEntity.name = :name order by re.username")
    List<ResourceEntity> findAllByOrganizationName(@Param("name") String name);

    @Query("select re from ResourceEntity re " +
            "join OrganizationEntity oe on oe.name = re.organizationEntity.name " +
            "join ClientEntity ce on ce.organizationEntity.name = oe.name " +
            "join ResourceProjectEntity rp on rp.resourceEntity.id = re.id " +
            "where rp.projectEntity.id = :projectId and ce.id = :clientId " +
            "order by re.username asc")
    List<ResourceEntity> findAllByOrganizationNameAndClientIdAndProjectEntityId(@Param("name") String name,
                                                                                @Param("clientId") Long clientId,
                                                                                @Param("projectId") Long projectId);
}
