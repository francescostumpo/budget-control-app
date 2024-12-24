package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.Organization;
import com.primesloth.budgetcontrolapp.api.model.User;
import com.primesloth.budgetcontrolapp.mappers.OrganizationMapper;
import com.primesloth.budgetcontrolapp.mappers.UserMapper;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final OrganizationMapper organizationMapper;
    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public ResponseEntity<List<Organization>> findAll() {
        var organizations = organizationRepository.findAll();
        return ResponseEntity.ok(organizationMapper.toOrganizationListDto(organizations));
    }

    public ResponseEntity<Organization> createOrganization(Organization organization) {
        var organizationEntity = organizationMapper.toOrganizationEntity(organization);
        var org = organizationRepository.save(organizationEntity);
        return ResponseEntity.ok(organizationMapper.toOrganizationDto(org));
    }

    public ResponseEntity<List<User>> getUsersByOrganizationName(String name) {
        if(organizationRepository.findByName(name).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }
        var users = userRepository.findAllByOrganizationName(name);
        return ResponseEntity.ok(userMapper.toUsersListDto(users));
    }
}
