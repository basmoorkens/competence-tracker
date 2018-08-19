package com.basm.ct.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.mapping.Subclass;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class EvaluationResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Version
    private int version;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="subcompetence_id")
    private SubCompetence subCompetence;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name ="student_id")
    private Student student;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "evaluation_id")
    private Evaluation evaluation;

    private boolean passed;

    public EvaluationResult(SubCompetence subCompetence, Student student) {
        this.subCompetence = subCompetence;
        this.student = student;
    }
}
