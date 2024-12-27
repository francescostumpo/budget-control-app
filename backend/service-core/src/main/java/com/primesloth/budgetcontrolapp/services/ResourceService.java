package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.AssociateResourceToProjectByOrganizationNameAndClientId200Response;
import com.primesloth.budgetcontrolapp.api.model.Resource;
import com.primesloth.budgetcontrolapp.entities.ResourceProjectEntity;
import com.primesloth.budgetcontrolapp.mappers.OrganizationMapper;
import com.primesloth.budgetcontrolapp.mappers.ProjectMapper;
import com.primesloth.budgetcontrolapp.mappers.ResourceMapper;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.ProjectRepository;
import com.primesloth.budgetcontrolapp.repositories.ResourceProjectRepository;
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
    private final ResourceProjectRepository resourceProjectRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    @Transactional
    public ResponseEntity<Resource> createResourceByOrganizationName(String name, Resource resource) {

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

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }

        var resources = resourceRepository.findAllByOrganizationName(name);
        return ResponseEntity.ok(resourceMapper.toResourceListDto(resources));
    }

    @Transactional
    public ResponseEntity<AssociateResourceToProjectByOrganizationNameAndClientId200Response> associateResourceToProjectByOrganizationNameAndClientId(String name, Integer clientId, Integer projectId, Resource resource) {
        var resourceEntityOptional = resourceRepository.findById(resource.getId().longValue());
        if(resourceEntityOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid resource id");
        }
        var projectEntityOptional = projectRepository.findById(projectId.longValue());
        if(projectEntityOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id");
        }
        var rpEntityOptional = resourceProjectRepository.findByResourceIdAndProjectId(resource.getId().longValue(), projectId.longValue());
        if(rpEntityOptional.isEmpty()){
            var rpEntity = new ResourceProjectEntity();
            rpEntity.setProjectEntity(projectEntityOptional.get());
            rpEntity.setResourceEntity(resourceEntityOptional.get());
            resourceProjectRepository.save(rpEntity);
        }

        var arp = new AssociateResourceToProjectByOrganizationNameAndClientId200Response();
        arp.setResource(resourceMapper.toResourceDto(resourceEntityOptional.get()));
        arp.setProject(projectMapper.toProjectDto(projectEntityOptional.get()));
        return ResponseEntity.ok(arp);
    }

    public ResponseEntity<List<Resource>> getAllResourceByClientIdAndProjectId(String name, Integer clientId, Integer projectId) {
        var resourceEntities = resourceRepository.findAllByOrganizationNameAndClientIdAndProjectEntityId(name, clientId.longValue(), projectId.longValue());
        return ResponseEntity.ok(resourceMapper.toResourceListDto(resourceEntities));
    }
}
