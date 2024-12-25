package com.primesloth.budgetcontrolapp.controllers;

import com.primesloth.budgetcontrolapp.api.model.Resource;
import com.primesloth.budgetcontrolapp.api.rest.ResourcesApi;
import com.primesloth.budgetcontrolapp.services.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ResourceController implements ResourcesApi {

    private final ResourceService resourceService;

    @Override
    public ResponseEntity<List<Resource>> getAllResourcesByOrganizationName(String name) {
        return resourceService.getAllResourcesByOrganizationName(name);
    }

    @Override
    public ResponseEntity<Resource> createResourceByOrganizationName(String name, Resource resource) {
        return resourceService.createResourceByOrganizationName(name, resource);
    }
}