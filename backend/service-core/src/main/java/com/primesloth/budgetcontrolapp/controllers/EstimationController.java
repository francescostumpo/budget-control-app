package com.primesloth.budgetcontrolapp.controllers;


import com.primesloth.budgetcontrolapp.api.model.CreateEstimationAssociatedToAProjectRequest;
import com.primesloth.budgetcontrolapp.api.model.Estimation;
import com.primesloth.budgetcontrolapp.api.model.UpdateEstimationAssociatedToAProject200Response;
import com.primesloth.budgetcontrolapp.api.rest.EstimationsApi;
import com.primesloth.budgetcontrolapp.services.EstimationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
public class EstimationController implements EstimationsApi {

    private final EstimationService estimationService;

    @Override
    public ResponseEntity<UpdateEstimationAssociatedToAProject200Response> createEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, CreateEstimationAssociatedToAProjectRequest createEstimationAssociatedToAProjectRequest) {
        return estimationService.createEstimationAssociatedToAProject(name, clientId, projectId, createEstimationAssociatedToAProjectRequest);
    }

    @Override
    public ResponseEntity<UpdateEstimationAssociatedToAProject200Response> updateEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, Estimation estimation) {
        return estimationService.updateEstimationAssociatedToAProject(name, clientId, projectId, estimation);
    }

    @Override
    public ResponseEntity<Estimation> getEstimationByProjectId(String name, Integer clientId, Integer projectId) {
        return estimationService.getEstimationByProjectId(name, clientId, projectId);
    }

    @Override
    public ResponseEntity<Void> deleteEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, CreateEstimationAssociatedToAProjectRequest createEstimationAssociatedToAProjectRequest) {
        return estimationService.deleteEstimationAssociatedToAProject(name, clientId, projectId, createEstimationAssociatedToAProjectRequest);
    }
}
