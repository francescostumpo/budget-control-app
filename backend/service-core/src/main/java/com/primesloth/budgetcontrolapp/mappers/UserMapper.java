package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Organization;
import com.primesloth.budgetcontrolapp.api.model.User;
import com.primesloth.budgetcontrolapp.entities.OrganizationEntity;
import com.primesloth.budgetcontrolapp.entities.UserEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
uses = OrganizationMapper.class)
public interface UserMapper {


    List<User> toUsersListDto(List<UserEntity> userEntities);

    @Mapping(source = "organizationEntity", target = "organization")
    User toUserDto(UserEntity userEntity);

    UserEntity toUserEntity(User user);
}
