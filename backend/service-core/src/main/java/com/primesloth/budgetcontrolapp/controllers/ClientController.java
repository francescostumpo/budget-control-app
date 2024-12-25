package com.primesloth.budgetcontrolapp.controllers;

import com.primesloth.budgetcontrolapp.api.model.Client;
import com.primesloth.budgetcontrolapp.api.rest.ClientsApi;
import com.primesloth.budgetcontrolapp.services.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ClientController implements ClientsApi {

    private final ClientService clientService;

    @Override
    public ResponseEntity<Client> createClientByOrganizationName(String name, Client client) {
        return clientService.createClientByOrganizationName(name, client);
    }

    @Override
    public ResponseEntity<Void> deleteClientByOrganizationNameAndClientId(String name, Integer id) {
        return ClientsApi.super.deleteClientByOrganizationNameAndClientId(name, id);
    }

    @Override
    public ResponseEntity<List<Client>> getAllClientsByOrganizationName(String name) {
        return clientService.getAllClientsByOrganizationName(name);
    }

    @Override
    public ResponseEntity<Client> getClientByOrganizationNameAndClientId(String name, Integer id) {
        return ClientsApi.super.getClientByOrganizationNameAndClientId(name, id);
    }

    @Override
    public ResponseEntity<Client> updateClientByOrganizationNameAndClientId(String name, Integer id, Client client) {
        return ClientsApi.super.updateClientByOrganizationNameAndClientId(name, id, client);
    }
}
