package com.primesloth.budgetcontrolapp.mappers;


import com.primesloth.budgetcontrolapp.api.model.Organization;
import com.primesloth.budgetcontrolapp.entities.OrganizationEntity;
import org.mapstruct.*;
import java.util.List;
@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface OrganizationMapper {

    List<Organization> toOrganizationListDto(List<OrganizationEntity> organizationEntities);

    Organization toOrganizationDto(OrganizationEntity organizationEntities);

    @Mapping(source = "id", target = "id", ignore = true)
    OrganizationEntity toOrganizationEntity(Organization organization);

}
