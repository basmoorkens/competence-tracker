package com.basm.ct.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class SubCompetenceCalculationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String competenceName;

    private String subCompetenceName;

    private int subCompetenceWeight;

    private boolean passed;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="competence_calculation_result_id")
    private CompetenceCalculationResult competenceCalculationResult;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "subCompetenceCalculationResult", cascade = CascadeType.ALL, orphanRemoval = true)
    List<SubCompetenceCalculationLine> subCompetenceCalculationLines;

    public void calculateIfSubCompetenceIsPassed() {
        int passedCounter = 0;
        int failedCounter = 0;
        for(SubCompetenceCalculationLine subCompetenceCalculationLine : subCompetenceCalculationLines) {
            if (subCompetenceCalculationLine.isPassed()) {
                passedCounter++;
            } else {
                failedCounter++;
            }
        }
        this.passed = (passedCounter >= failedCounter);
    }

    public void addSubCompetenceCalculationLine(SubCompetenceCalculationLine subCompetenceCalculationLine) {
        if(subCompetenceCalculationLines == null) {
            subCompetenceCalculationLines = new ArrayList<>();
        }
        subCompetenceCalculationLines.add(subCompetenceCalculationLine);
    }
}
