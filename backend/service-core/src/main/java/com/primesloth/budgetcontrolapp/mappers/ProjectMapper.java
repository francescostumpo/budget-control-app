package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Project;
import com.primesloth.budgetcontrolapp.entities.ProjectEntity;
import com.primesloth.budgetcontrolapp.entities.mongo.ProjectMongoEntity;
import com.primesloth.budgetcontrolapp.entities.mongo.ProjectionMongoEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    @Mapping(target = "id", ignore = true)
    ProjectEntity toProjectEntity(Project project);

    Project toProjectDto(ProjectEntity projectEntity);

    List<Project> toProjectListDto(List<ProjectEntity> projectEntityList);

    ProjectMongoEntity toProjectMongo(ProjectEntity projectEntity);
}
