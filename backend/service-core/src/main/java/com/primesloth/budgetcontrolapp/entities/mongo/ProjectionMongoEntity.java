package com.primesloth.budgetcontrolapp.entities.mongo;


import lombok.Data;


import java.time.LocalDate;


@Data
public class ProjectionMongoEntity {

    private LocalDate endFortnight;
    private Double availableHours;
    private Double chargedHours;
    private Double cost;
    private Boolean isActual;
}
