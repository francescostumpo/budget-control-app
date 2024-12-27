package com.primesloth.budgetcontrolapp.entities.mongo;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Data
public class ResourceMongoEntity {

    private String username;
    private Double lcr;
    private Double chargeability;
    @Field("projections")
    private List<ProjectionMongoEntity> projections;

}
