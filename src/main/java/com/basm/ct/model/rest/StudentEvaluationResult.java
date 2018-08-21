package com.basm.ct.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class StudentEvaluationResult {

    private Date evaluationDate;

    private String evaluationName;

    private String subCompetenceName;

    private String studentName;

    private boolean passed;

    public StudentEvaluationResult(final String evaluationName, final String subCompetenceName, final String studentName, final boolean passed, final Date evaluationDate) {
        this.evaluationDate = evaluationDate;
        this.evaluationName = evaluationName;
        this.subCompetenceName = subCompetenceName;
        this.studentName = studentName;
        this.passed = passed;
    }
}
