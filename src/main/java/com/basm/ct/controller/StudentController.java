package com.basm.ct.controller;

import com.basm.ct.model.EvaluationResult;
import com.basm.ct.model.SchoolClass;
import com.basm.ct.model.Student;
import com.basm.ct.model.rest.StudentEvaluationResult;
import com.basm.ct.model.rest.StudentViewModel;
import com.basm.ct.repository.SchoolClassRepository;
import com.basm.ct.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private SchoolClassRepository schoolClassRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/students", method =  RequestMethod.GET, produces = "application/json")
    public List<StudentViewModel> getStudents (){
        List<StudentViewModel> students = new ArrayList<>();
        for (Student student : studentRepository.findAll()) {
            StudentViewModel studentViewModel = new StudentViewModel(student.getId(), student.getFirstName(), student.getName(), student.getSchoolClass().getName(), new ArrayList<>());
            students.add(studentViewModel);
        }
        return students;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/students/schoolclass/{id}", method =  RequestMethod.GET, produces = "application/json")
    public List<StudentViewModel> getStudentsBySchoolClassId (@PathVariable("id") final Long schoolClassId){
        SchoolClass foundClass = schoolClassRepository.findById(schoolClassId).get();
        List<StudentViewModel> students = new ArrayList<>();
        for (Student student : foundClass.getStudents()) {
            StudentViewModel studentViewModel = new StudentViewModel(student.getId(), student.getFirstName(), student.getName(), student.getSchoolClass().getName(), new ArrayList<>());
            students.add(studentViewModel);
        }
        return students;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/students/{id}", method =  RequestMethod.GET, produces = "application/json")
    public StudentViewModel getFullStudentById (@PathVariable("id") final Long studentId){
        Student student = studentRepository.findById(studentId).get();
        StudentViewModel studentViewModel = new StudentViewModel(student.getId(), student.getFirstName(), student.getName(), student.getSchoolClass().getName(), new ArrayList<>());
        for(EvaluationResult evaluationResult : student.getEvaluationResults()) {
            studentViewModel.getStudentEvaluationResult().add(new StudentEvaluationResult(evaluationResult.getEvaluation().getName(),
                    evaluationResult.getSubCompetence().getName(), evaluationResult.getStudent().getName(),
                    evaluationResult.isPassed(), evaluationResult.getEvaluation().getEvaluationDate()));
        }
        return studentViewModel;
    }
}
