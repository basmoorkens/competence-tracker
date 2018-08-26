package com.basm.ct.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="student_id")
    private Student student;

    private Date startDate;

    private Date endDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "report", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CompetenceCalculationResult> competenceCalculationResults;

    public void addCompetenceCalculationResult(final CompetenceCalculationResult competenceCalculationResult ) {
        if(competenceCalculationResults == null) {
            competenceCalculationResults = new ArrayList<>();
        }
        competenceCalculationResults.add(competenceCalculationResult);
    }
}
