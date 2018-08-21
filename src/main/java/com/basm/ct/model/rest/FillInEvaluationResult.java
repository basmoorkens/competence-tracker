package com.basm.ct.model.rest;

import com.basm.ct.model.Student;
import com.basm.ct.model.SubCompetence;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class FillInEvaluationResult implements Serializable {

    private Long id;

    private Long evaluationId;

    private Long studentId;

    private Long subCompetenceId;

    private boolean passed;

    public FillInEvaluationResult(final Long id, final Long evaluationId, final Student student, final SubCompetence subCompetence, final boolean passed) {
        this.id = id;
        this.evaluationId = evaluationId;
        this.studentId = student.getId();
        this.subCompetenceId = subCompetence.getId();
        this.passed = passed;
    }
}
