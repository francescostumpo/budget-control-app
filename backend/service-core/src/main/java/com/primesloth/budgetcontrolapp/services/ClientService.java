package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.Client;
import com.primesloth.budgetcontrolapp.mappers.ClientMapper;
import com.primesloth.budgetcontrolapp.repositories.ClientRepository;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientService {

    private final OrganizationRepository organizationRepository;
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;


    public ResponseEntity<Client> createNewClientByOrganizationName(String name, Client client) {
        if(Objects.isNull(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization name parameter.");
        }

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }

        var clientEntity = clientMapper.toClientEntity(client);
        clientEntity.setOrganizationEntity(orgOptional.get());
        var ce = clientRepository.save(clientEntity);
        return ResponseEntity.ok(clientMapper.toClientDto(ce));

    }

    public ResponseEntity<List<Client>> getAllClientsByOrganizationName(String name) {
        if(Objects.isNull(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization name parameter.");
        }

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }
        var clientEntityList = clientRepository.findAllByOrganizationName(name);
        return ResponseEntity.ok(clientMapper.toClientListDto(clientEntityList));
    }
}
