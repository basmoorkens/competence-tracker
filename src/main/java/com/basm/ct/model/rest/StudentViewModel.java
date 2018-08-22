package com.basm.ct.model.rest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class StudentViewModel {

    private Long id;

    private String firstName;

    private String name;

    private String schoolClassName;

    private List<StudentEvaluationResult> studentEvaluationResult;

    public StudentViewModel(final Long id, final String firstname, final String name, final String schoolClassName, final List<StudentEvaluationResult> studentEvaluationResults) {
        this.id = id;
        this.firstName = firstname;
        this.name = name;
        this.schoolClassName = schoolClassName;
        this.studentEvaluationResult = studentEvaluationResults;
    }

}
