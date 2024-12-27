package com.primesloth.budgetcontrolapp.entities.mongo;


import jakarta.persistence.Id;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;


import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "estimations")
public class EstimationMongoEntity {

    @Id
    private String id;
    private Long version;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private ProjectMongoEntity project;


    @Field("resources")
    private List<ResourceMongoEntity> resourceMongoEntities;


    private Double expectedCost = 0.0;

    private Double expectedRevenues = 0.0;

    private Double saving = 0.0;

}
