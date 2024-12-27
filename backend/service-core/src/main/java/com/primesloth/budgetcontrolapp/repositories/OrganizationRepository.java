package com.primesloth.budgetcontrolapp.repositories;

import com.primesloth.budgetcontrolapp.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Long> {

    Optional<OrganizationEntity> findByName(String name);

    @Query("select oe from OrganizationEntity oe join ClientEntity ce on ce.organizationEntity.id = oe.id " +
            "where ce.id = :id")
    Optional<OrganizationEntity> findByClientId(@Param("id") Long id);
}
