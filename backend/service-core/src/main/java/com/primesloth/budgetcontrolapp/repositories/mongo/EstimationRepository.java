package com.primesloth.budgetcontrolapp.repositories.mongo;

import com.primesloth.budgetcontrolapp.entities.mongo.EstimationMongoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstimationRepository extends MongoRepository<EstimationMongoEntity, String> {

    @Query("{ 'project._id' : ?0 }")
    Optional<EstimationMongoEntity> findEstimationMongoEntityByProjectId(Long projectId);

    @Query(value = "{ 'project._id' : ?0 }", delete = true)
    void deleteEstimationMongoEntityByProjectId(Long projectId);
}
