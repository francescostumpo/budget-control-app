package com.primesloth.budgetcontrolapp.repositories;

import com.primesloth.budgetcontrolapp.entities.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClientRepository extends JpaRepository<ClientEntity, Long> {

    @Query("select ue from ClientEntity ue where ue.organizationEntity.name = :name order by ue.createdAt")
    List<ClientEntity> findAllByOrganizationName(@Param("name") String name);

    @Query("select ce from ClientEntity ce where ce.organizationEntity.name = :name and ce.id = :id")
    Optional<ClientEntity> findByOrganizationNameAndId(@Param("name") String name, @Param("id") Long id);
}
