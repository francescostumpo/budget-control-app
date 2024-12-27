package com.primesloth.budgetcontrolapp.services;

import com.primesloth.budgetcontrolapp.api.model.CreateEstimationAssociatedToAProjectRequest;
import com.primesloth.budgetcontrolapp.api.model.Estimation;
import com.primesloth.budgetcontrolapp.api.model.UpdateEstimationAssociatedToAProject200Response;
import com.primesloth.budgetcontrolapp.entities.mongo.EstimationMongoEntity;
import com.primesloth.budgetcontrolapp.entities.mongo.ProjectionMongoEntity;
import com.primesloth.budgetcontrolapp.entities.mongo.ResourceMongoEntity;
import com.primesloth.budgetcontrolapp.mappers.EstimationMapper;
import com.primesloth.budgetcontrolapp.mappers.ProjectMapper;
import com.primesloth.budgetcontrolapp.repositories.ClientRepository;
import com.primesloth.budgetcontrolapp.repositories.OrganizationRepository;
import com.primesloth.budgetcontrolapp.repositories.ProjectRepository;
import com.primesloth.budgetcontrolapp.repositories.ResourceRepository;
import com.primesloth.budgetcontrolapp.repositories.mongo.EstimationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EstimationService {

    private final EstimationRepository estimationRepository;
    private final ProjectRepository projectRepository;
    private final ResourceRepository resourceRepository;
    private final ProjectMapper projectMapper;
    private final EstimationMapper estimationMapper;
    private final ClientRepository clientRepository;
    private final OrganizationRepository organizationRepository;
    private List<ProjectionMongoEntity> calculateProjectionsFortnights(LocalDate startDate, LocalDate endDate, Double chargeability, Double lcr, EstimationMongoEntity estimation) {
        List<ProjectionMongoEntity> projections = new ArrayList<>();

        LocalDate current = startDate.withDayOfMonth(1); // Start at the beginning of the month
        while (!current.isAfter(endDate)) {
            // First fortnight: 1st to 15th
            LocalDate firstFortnightStart = current;
            LocalDate firstFortnightEnd = current.withDayOfMonth(15);
            if (!firstFortnightEnd.isAfter(endDate)) {
                projections.add(createProjection(firstFortnightStart, firstFortnightEnd, chargeability, lcr, estimation));
            }

            // Second fortnight: 16th to the end of the month
            LocalDate secondFortnightStart = current.withDayOfMonth(16);
            LocalDate secondFortnightEnd = current.with(TemporalAdjusters.lastDayOfMonth());
            if (!secondFortnightEnd.isAfter(endDate)) {
                projections.add(createProjection(secondFortnightStart, secondFortnightEnd, chargeability, lcr, estimation));
            }

            // Move to the next month
            current = current.plusMonths(1).withDayOfMonth(1);
        }

        return projections;
    }

    private ProjectionMongoEntity createProjection(LocalDate start, LocalDate end, Double chargeability, Double lcr, EstimationMongoEntity estimation) {
        var workingHours = calculateWorkingHours(start, end);
        ProjectionMongoEntity projection = new ProjectionMongoEntity();
        projection.setEndFortnight(end);
        projection.setAvailableHours(workingHours);
        projection.setChargedHours(new BigDecimal(workingHours * chargeability).setScale(2, RoundingMode.HALF_UP).doubleValue()); // Default value
        projection.setCost(new BigDecimal(workingHours * chargeability * lcr).setScale(2, RoundingMode.HALF_UP).doubleValue());        // Default value
        projection.setIsActual(false); // Default value
        estimation.setExpectedCost(BigDecimal.valueOf(estimation.getExpectedCost() + projection.getCost()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        return projection;
    }

    private double calculateWorkingHours(LocalDate start, LocalDate end) {
        double totalHours = 0;
        LocalDate current = start;
        while (!current.isAfter(end)) {
            DayOfWeek dayOfWeek = current.getDayOfWeek();
            if (dayOfWeek != DayOfWeek.SATURDAY && dayOfWeek != DayOfWeek.SUNDAY) {
                totalHours += 8; // 8 hours per working day
            }
            current = current.plusDays(1);
        }
        return totalHours;
    }

    public ResponseEntity<UpdateEstimationAssociatedToAProject200Response> createEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, CreateEstimationAssociatedToAProjectRequest createEstimationAssociatedToAProjectRequest) {
        var projectOptional = projectRepository.findById(projectId.longValue());
        if(projectOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id.");
        }

        var project = projectOptional.get();
        var resources = resourceRepository.findAllByOrganizationNameAndClientIdAndProjectEntityId(name, clientId.longValue(), projectId.longValue());
        if(resources.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No resources associated to project id.");
        }
        var estimation = new EstimationMongoEntity();
        estimation.setVersion(1L);
        estimation.setCreatedAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        estimation.setProject(projectMapper.toProjectMongo(project));
        var resourcesMongoEntityProjections = new ArrayList<ResourceMongoEntity>();
        resources.forEach(re -> {
            var resourceMongoEntity = new ResourceMongoEntity();
            resourceMongoEntity.setUsername(re.getUsername());
            resourceMongoEntity.setLcr(re.getLcr());
            resourceMongoEntity.setChargeability(re.getChargeability());
            resourceMongoEntity.setProjections(
                    calculateProjectionsFortnights(project.getStartDate(), project.getEndDate(),
                            resourceMongoEntity.getChargeability(), resourceMongoEntity.getLcr(),
                            estimation));
            resourcesMongoEntityProjections.add(resourceMongoEntity);
        });
        estimation.setResourceMongoEntities(resourcesMongoEntityProjections);
        // Formula to calculate revenues based on cost and CCI
        estimation.setExpectedRevenues(BigDecimal.valueOf(estimation.getExpectedCost() / (1 - (estimation.getProject().getCci() / 100))).setScale(2, RoundingMode.HALF_UP).doubleValue());
        estimation.setSaving(BigDecimal.valueOf(estimation.getProject().getRevenues() - estimation.getExpectedRevenues()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        var estimationResp = estimationRepository.save(estimation);

        updateClientAndOrganizationEntity(estimation, true);


        var resp = new UpdateEstimationAssociatedToAProject200Response();
        resp.setEstimation(estimationMapper.toEstimationDto(estimationResp));
        return ResponseEntity.status(HttpStatus.CREATED).body(resp);

    }

    public ResponseEntity<UpdateEstimationAssociatedToAProject200Response> updateEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, Estimation estimation) {
        if(!Objects.equals(estimation.getProject().getId(), projectId) || estimationRepository.findById(estimation.getId()).isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid combination of project and estimation id.");
        }
        var estimationMongoEntity = estimationMapper.toEstimationMongoEntity(estimation);
        estimationMongoEntity.setVersion(estimation.getVersion() + 1);
        estimationMongoEntity.setUpdatedAt(ZonedDateTime.now(ZoneId.of("Europe/Rome")).toLocalDateTime());
        var est = estimationRepository.save(estimationMongoEntity);
        var resp = new UpdateEstimationAssociatedToAProject200Response();
        resp.setEstimation(estimationMapper.toEstimationDto(est));
        return ResponseEntity.ok(resp);
    }

    public ResponseEntity<Estimation> getEstimationByProjectId(String name, Integer clientId, Integer projectId) {
        return estimationRepository.findEstimationMongoEntityByProjectId(projectId.longValue())
                .map(
                        estimationMongoEntity ->
                                ResponseEntity.ok(estimationMapper.toEstimationDto(estimationMongoEntity)))
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NO_CONTENT).body(new Estimation()));

    }

    private void updateClientAndOrganizationEntity(EstimationMongoEntity estimation, boolean toBeAdded){
        var clientOptional = clientRepository.findByProjectId(estimation.getProject().getId());
        if(clientOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No associated clients to project.");
        }
        var clientEntity = clientOptional.get();
        if(toBeAdded){
            clientEntity.setTotalSaving(BigDecimal.valueOf(clientOptional.get().getTotalSaving() + estimation.getSaving()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }else{
            clientEntity.setTotalSaving(BigDecimal.valueOf(clientOptional.get().getTotalSaving() - estimation.getSaving()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        clientRepository.save(clientEntity);

        var orgOptional = organizationRepository.findByClientId(clientOptional.get().getId());
        if(orgOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "No associated org to client.");
        }
        var orgEntity = orgOptional.get();
        if(toBeAdded){
            orgEntity.setTotalSaving(BigDecimal.valueOf(orgEntity.getTotalSaving() + estimation.getSaving()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }else {
            orgEntity.setTotalSaving(BigDecimal.valueOf(orgEntity.getTotalSaving() - estimation.getSaving()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        }
        organizationRepository.save(orgEntity);
    }

    public ResponseEntity<Void> deleteEstimationAssociatedToAProject(String name, Integer clientId, Integer projectId, CreateEstimationAssociatedToAProjectRequest createEstimationAssociatedToAProjectRequest) {
        var projectOptional = projectRepository.findById(projectId.longValue());
        if(projectOptional.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid project id.");
        }
        var estimation = estimationRepository.findEstimationMongoEntityByProjectId(projectId.longValue());
        if(estimation.isEmpty()){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No associated estimation to project.");
        }
        updateClientAndOrganizationEntity(estimation.get(), false);
        estimationRepository.deleteEstimationMongoEntityByProjectId(projectId.longValue());

        return ResponseEntity.ok(null);
    }
}
