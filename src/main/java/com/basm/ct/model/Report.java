package com.basm.ct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

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

    @ManyToMany
    @JoinTable(
            name = "report_evaluation_result",
            joinColumns = {@JoinColumn(name = "report_id")},
            inverseJoinColumns = {@JoinColumn(name = "evaluationresult_id")}
    )
    @JsonIgnore
    private Set<EvaluationResult> evaluationResults;
}
