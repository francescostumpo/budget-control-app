package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.Resource;
import com.primesloth.budgetcontrolapp.mappers.OrganizationMapper;
import com.primesloth.budgetcontrolapp.mappers.ResourceMapper;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.ResourceRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ResourceService {

    private final OrganizationRepository organizationRepository;
    private final ResourceMapper resourceMapper;
    private final ResourceRepository resourceRepository;
    @Transactional
    public ResponseEntity<Resource> createResourceByOrganizationName(String name, Resource resource) {
        if(Objects.isNull(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization name parameter.");
        }

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }

        var resourceEntity = resourceMapper.toResourceEntity(resource);
        resourceEntity.setOrganizationEntity(orgOptional.get());
        var r = resourceRepository.save(resourceEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(resourceMapper.toResourceDto(r));
    }

    public ResponseEntity<List<Resource>> getAllResourcesByOrganizationName(String name) {
        if(Objects.isNull(name)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization name parameter.");
        }

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }

        var resources = resourceRepository.findAllByOrganizationName(name);
        return ResponseEntity.ok(resourceMapper.toResourceListDto(resources));
    }
}
