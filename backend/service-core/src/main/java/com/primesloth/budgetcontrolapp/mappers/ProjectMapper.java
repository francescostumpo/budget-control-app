package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Project;
import com.primesloth.budgetcontrolapp.entities.ProjectEntity;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProjectMapper {

    ProjectEntity toProjectEntity(Project project);

    Project toProjectDto(ProjectEntity projectEntity);

    List<Project> toProjectListDto(List<ProjectEntity> projectEntityList);
}
