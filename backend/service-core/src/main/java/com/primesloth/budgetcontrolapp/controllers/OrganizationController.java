package com.primesloth.budgetcontrolapp.controllers;

import com.primesloth.budgetcontrolapp.api.model.Organization;
import com.primesloth.budgetcontrolapp.api.model.User;
import com.primesloth.budgetcontrolapp.api.rest.OrganizationsApi;
import com.primesloth.budgetcontrolapp.services.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrganizationController implements OrganizationsApi {

    private final OrganizationService organizationService;

    @Override
    public ResponseEntity<Organization> createOrganization(Organization organization) {
        return organizationService.createOrganization(organization);
    }

    @Override
    public ResponseEntity<Void> deleteOrganization(String organizationName) {
        return OrganizationsApi.super.deleteOrganization(organizationName);
    }

    @Override
    public ResponseEntity<Organization> getOrganizationById(String organizationName) {
        return OrganizationsApi.super.getOrganizationById(organizationName);
    }

    @Override
    public ResponseEntity<List<Organization>> getOrganizations() {
        return organizationService.findAll();
    }

    @Override
    public ResponseEntity<Organization> updateOrganization(String organizationName, Organization organization) {
        return OrganizationsApi.super.updateOrganization(organizationName, organization);
    }

    @Override
    public ResponseEntity<List<User>> getUsersByOrganizationName(String name) {
        return organizationService.getUsersByOrganizationName(name);
    }
}
