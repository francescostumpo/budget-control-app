package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.Project;
import com.primesloth.budgetcontrolapp.entities.ResourceProjectEntity;
import com.primesloth.budgetcontrolapp.mappers.ProjectMapper;
import com.primesloth.budgetcontrolapp.repositories.ClientRepository;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.ProjectRepository;
import com.primesloth.budgetcontrolapp.repositories.ResourceProjectRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectService {

    private final ClientRepository clientRepository;
    private final ProjectRepository projectRepository;
    private final ProjectMapper projectMapper;
    private final OrganizationRepository organizationRepository;
    private final ResourceProjectRepository resourceProjectRepository;
    public ResponseEntity<List<Project>> getAllProjectsByOrganizationNameAndClientId(String name, Integer id) {
        var clientOptional = clientRepository.findByOrganizationNameAndId(name, id.longValue());
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid combination of Organization and client.");
        }
        var projectEntities = projectRepository.findAllByClientId(id.longValue());
        return ResponseEntity.ok(projectMapper.toProjectListDto(projectEntities));

    }

    @Transactional
    public ResponseEntity<Project> createProjectByOrganizationNameAndClientId(String name, Integer id, Project project) {
        var clientOptional = clientRepository.findByOrganizationNameAndId(name, id.longValue());
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid combination of Organization and client.");
        }
        var projectEntity = projectMapper.toProjectEntity(project);
        projectEntity.setClientEntity(clientOptional.get());

        var prj = projectRepository.save(projectEntity);

        var clientEntity = clientOptional.get();
        clientEntity.setTotalSold(BigDecimal.valueOf(clientEntity.getTotalSold() + projectEntity.getRevenues()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        clientRepository.save(clientEntity);

        var orgOptional = organizationRepository.findByClientId(clientEntity.getId());
        if (orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No associated org to client.");
        }
        var orgEntity = orgOptional.get();
        orgEntity.setTotalSold(BigDecimal.valueOf(orgEntity.getTotalSold() + projectEntity.getRevenues()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return ResponseEntity.status(HttpStatus.CREATED).body(projectMapper.toProjectDto(prj));
    }

    @Transactional
    public ResponseEntity<Void> deleteProjectByOrganizationNameAndClientId(String name, Integer clientId, Integer projectId) {
        var clientOptional = clientRepository.findByOrganizationNameAndId(name, clientId.longValue());
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid combination of Organization and client.");
        }
        var projOptional = projectRepository.findById(projectId.longValue());
        if(projOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid projectId.");
        }

        var orgEntity = clientOptional.get().getOrganizationEntity();
        orgEntity.setTotalSold(BigDecimal.valueOf(orgEntity.getTotalSold() - projOptional.get().getRevenues()).doubleValue());
        organizationRepository.save(orgEntity);

        var clientEntity = clientOptional.get();
        clientEntity.setTotalSold(BigDecimal.valueOf(clientEntity.getTotalSold() - projOptional.get().getRevenues()).doubleValue());
        clientRepository.save(clientEntity);

        resourceProjectRepository.deleteAllByProjectId(projectId.longValue());

        projectRepository.deleteById(projectId.longValue());

        return ResponseEntity.ok(null);
    }
}
