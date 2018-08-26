package com.basm.ct.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class CompetenceCalculationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    private String competenceName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "competenceCalculationResult", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SubCompetenceCalculationResult> subCompetenceCalculationResults;

    private boolean passed;

    private double maxScore;

    private double obtainedScore;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="report_id")
    private Report report;

    public void addSubCompetenceCalculationResult(final SubCompetenceCalculationResult toAdd) {
        if(subCompetenceCalculationResults == null) {
            subCompetenceCalculationResults = new ArrayList<>();
        }
        subCompetenceCalculationResults.add(toAdd);
    }

    public void calculateCompetenceResults() {
        for(SubCompetenceCalculationResult subResult : subCompetenceCalculationResults) {
            maxScore += subResult.getSubCompetenceWeight();
            if(subResult.isPassed()) {
                obtainedScore += subResult.getSubCompetenceWeight();
            }
        }

        if((double)obtainedScore >= (double)(maxScore / 2) ) {
            passed = true;
        } else {
            passed = false;
        }
    }
}
