package com.basm.ct.controller;

import com.basm.ct.model.Evaluation;
import com.basm.ct.model.rest.FillInEvaluationResult;
import com.basm.ct.repository.EvaluationRepository;
import com.basm.ct.repository.EvaluationResultRepository;
import com.basm.ct.util.EvaluationResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
public class EvaluationController {

    @Autowired
    private EvaluationRepository evaluationRepository;

    @Autowired
    private EvaluationResultRepository evaluationResultRepository;


    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations/{id}", method =  RequestMethod.GET, produces = "application/json")
    public Evaluation getEvaluation (@PathVariable("id") final Long evaluationId){
        Evaluation evaluation = evaluationRepository.findById(evaluationId).get();
        evaluation.setFillInEvaluationResults(EvaluationResultUtil.convertEvaluationResultsToFillInEvaluationResultModel(evaluation.getEvaluationResults()));
        return evaluation;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations", method =  RequestMethod.GET, produces = "application/json")
    public List<Evaluation> getEvaluations() {
        List<Evaluation> evaluations = new ArrayList<>();
        evaluationRepository.findAll().forEach(evaluations::add);
        return evaluations;
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluations/generate", method =  RequestMethod.POST, produces = "application/json")
    public List<FillInEvaluationResult> generateEvaluationResults(@Valid @RequestBody  Evaluation evaluation) {
        evaluation.generateEvaluationResults();
        evaluation = evaluationRepository.save(evaluation);
        return EvaluationResultUtil.convertEvaluationResultsToFillInEvaluationResultModel(evaluation.getEvaluationResults());
    }

    @CrossOrigin(origins = "*", allowedHeaders = "*")
    @RequestMapping(value = "/evaluationresults/save", method =  RequestMethod.POST)
    public ResponseEntity  saveEvaluationResults(@Valid @RequestBody final List<FillInEvaluationResult> evaluationResults) {
        List<Long> passedIds = new ArrayList<>();
        List<Long> failedIds = new ArrayList<>();
        for(FillInEvaluationResult fillInEvaluationResult : evaluationResults) {
            if(fillInEvaluationResult.getId()==null) {
                throw new RuntimeException("Invalid evaluation result in request.");
            }
            if(fillInEvaluationResult.isPassed()) {
                passedIds.add(fillInEvaluationResult.getId());
            } else {
                failedIds.add(fillInEvaluationResult.getId());
            }
        }
        if(failedIds.size() > 0) {
            evaluationResultRepository.setResultsToFailedForIds(failedIds);
        }
        if(passedIds.size() > 0 ) {
            evaluationResultRepository.setResultsToPassedForIds(passedIds);
        }
        return new ResponseEntity(HttpStatus.OK);
    }
}
