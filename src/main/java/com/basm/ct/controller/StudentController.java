package com.basm.ct.controller;

import com.basm.ct.model.EvaluationResult;
import com.basm.ct.model.Student;
import com.basm.ct.model.rest.StudentEvaluationResult;
import com.basm.ct.model.rest.StudentViewModel;
import com.basm.ct.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/students", method =  RequestMethod.GET, produces = "application/json")
    public List<StudentViewModel> getStudents (){
        List<StudentViewModel> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            StudentViewModel studentViewModel = new StudentViewModel(student.getFirstName(), student.getName(), student.getSchoolClass().getName(), new ArrayList<>());
            students.add(studentViewModel);
        }
        return students;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/students/{id}", method =  RequestMethod.GET, produces = "application/json")
    public List<StudentEvaluationResult> getEvaluationResultsForStudent (@PathVariable("id") final Long studentId){
        List<EvaluationResult> evaluationResults = studentRepository.getEvaluationResultsForStudentId(studentId);
        List<StudentEvaluationResult> studentEvaluationResults = new ArrayList<>();
        for(EvaluationResult evaluationResult : evaluationResults) {
            studentEvaluationResults.add(new StudentEvaluationResult(evaluationResult.getEvaluation().getName(),
                    evaluationResult.getSubCompetence().getName(), evaluationResult.getStudent().getName(),
                    evaluationResult.isPassed(), evaluationResult.getEvaluation().getEvaluationDate()));
        }
        return studentEvaluationResults;
    }
}
