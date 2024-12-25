package com.primesloth.budgetcontrolapp.controllers;

import com.primesloth.budgetcontrolapp.api.model.Project;
import com.primesloth.budgetcontrolapp.api.rest.ProjectsApi;
import com.primesloth.budgetcontrolapp.services.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProjectController implements ProjectsApi {

    private final ProjectService projectService;

    @Override
    public ResponseEntity<Project> createProjectByOrganizationNameAndClientId(String name, Integer id, Project project) {
        return projectService.createProjectByOrganizationNameAndClientId(name, id, project);
    }

    @Override
    public ResponseEntity<List<Project>> getAllProjectsByOrganizationNameAndClientId(String name, Integer id) {
        return projectService.getAllProjectsByOrganizationNameAndClientId(name, id);
    }
}
