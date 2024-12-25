package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.Project;
import com.primesloth.budgetcontrolapp.mappers.ProjectMapper;
import com.primesloth.budgetcontrolapp.repositories.ClientRepository;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final OrganizationRepository organizationRepository;
    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    public ResponseEntity<List<Project>> getAllProjectsByOrganizationNameAndClientId(String name, Integer id) {
        if(Objects.isNull(name) || Objects.isNull(id)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Missing organization name or client id parameter.");
        }

        var orgOptional = organizationRepository.findByName(name);
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid organization.");
        }
        var clientOptional = clientRepository.findByOrganizationNameAndId(name, id.longValue());
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid client.");
        }
        var projectEntities = projectRepository.findAllByClientId(id.longValue());
        return ResponseEntity.ok(projectMapper.toProjectListDto(projectEntities));

    }

    public ResponseEntity<Project> createProjectByOrganizationNameAndClientId(String name, Integer id, Project project) {
        return null;
    }
}
