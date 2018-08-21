package com.basm.ct.model;

import com.basm.ct.model.rest.FillInEvaluationResult;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Evaluation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotBlank
    private String name;

    private Date evaluationDate;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="school_class_id")
    private SchoolClass schoolClass;

    @ManyToMany
    @JoinTable(
            name = "evaluation_subcompetences",
            joinColumns = {@JoinColumn(name = "evaluation_id")},
            inverseJoinColumns = {@JoinColumn(name = "subcompetence_id")}
    )
    private Set<SubCompetence> subCompetences;

    @Transient
    private List<FillInEvaluationResult> fillInEvaluationResults;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "evaluation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EvaluationResult> evaluationResults;

    public void generateEvaluationResults() {
        for(Student student : schoolClass.getStudents()){
            for(SubCompetence subCompetence : subCompetences) {
                addEvaluationResult(new EvaluationResult(subCompetence, student));
            }
        }
    }

    public void addEvaluationResult(EvaluationResult result) {
        if(evaluationResults == null) {
            evaluationResults = new ArrayList<>();
        }
        evaluationResults.add(result);
        result.setEvaluation(this);
    }
}
