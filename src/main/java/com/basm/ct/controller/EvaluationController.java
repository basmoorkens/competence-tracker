package com.basm.ct.controller;

import com.basm.ct.model.Evaluation;
import com.basm.ct.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations", method =  RequestMethod.GET, produces = "application/json")
    public List<Evaluation> getSchoolClasses() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        return evaluations;
    }
}
