package com.primesloth.budgetcontrolapp.mappers;

import com.primesloth.budgetcontrolapp.api.model.Client;
import com.primesloth.budgetcontrolapp.entities.ClientEntity;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING,
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ClientMapper {

    List<Client> toClientListDto(List<ClientEntity> clientEntities);

    @Mapping(target = "id", ignore = true)
    ClientEntity toClientEntity(Client client);

    Client toClientDto(ClientEntity clientEntity);
}
