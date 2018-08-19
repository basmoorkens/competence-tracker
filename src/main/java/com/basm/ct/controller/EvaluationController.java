package com.basm.ct.controller;

import com.basm.ct.model.Competence;
import com.basm.ct.model.Evaluation;
import com.basm.ct.repository.EvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations", method =  RequestMethod.GET, produces = "application/json")
    public List<Evaluation> getEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        return evaluations;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations/generate", method =  RequestMethod.POST, produces = "application/json")
    public Evaluation generateEvaluationResults(@Valid @RequestBody final Evaluation evaluation) {
        evaluation.generateEvaluationResults();
        return evaluationRepository.save(evaluation);
    }
}
