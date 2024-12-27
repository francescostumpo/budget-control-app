package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Estimation;
import com.primesloth.budgetcontrolapp.api.model.ResourceProjection;
import com.primesloth.budgetcontrolapp.entities.mongo.EstimationMongoEntity;
import com.primesloth.budgetcontrolapp.entities.mongo.ResourceMongoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EstimationMapper {

    List<Estimation> toEstimationListDto(List<EstimationMongoEntity> estimationMongoEntities);

    @Mapping(source = "resourceMongoEntities", target = "resourcesProjections")
    @Mapping(expression = "java(resourceMongoEntities.getProjections())", target = "resourcesProjections.projections")
    Estimation toEstimationDto(EstimationMongoEntity estimationMongoEntity);

    @Mapping(target = "resourceMongoEntities", source = "resourcesProjections")
    @Mapping(expression = "java(resourcesProjections.getProjections())", target = "resourceMongoEntities.projections")
    EstimationMongoEntity toEstimationMongoEntity(Estimation estimation);

}
