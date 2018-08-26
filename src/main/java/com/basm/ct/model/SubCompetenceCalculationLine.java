package com.basm.ct.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class SubCompetenceCalculationLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String evaluationName;

    private boolean passed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="subcompetence_calculation_result_id")
    private SubCompetenceCalculationResult subCompetenceCalculationResult;

}
