package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Resource;
import com.primesloth.budgetcontrolapp.entities.ResourceEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ResourceMapper {

    @Mapping(target = "id", ignore = true)
    ResourceEntity toResourceEntity(Resource resource);

    Resource toResourceDto(ResourceEntity resourceEntity);

    List<Resource> toResourceListDto(List<ResourceEntity> resourceEntities);
}
