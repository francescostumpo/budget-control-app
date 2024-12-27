package com.primesloth.budgetcontrolapp.entities.mongo;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProjectMongoEntity {

    private Long id;
    private String name;
    private Double revenues;
    private Double cci;
    private LocalDate startDate;
    private LocalDate endDate;
}
