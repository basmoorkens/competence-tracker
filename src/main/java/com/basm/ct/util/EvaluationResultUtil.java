package com.basm.ct.util;

import com.basm.ct.model.EvaluationResult;
import com.basm.ct.model.rest.FillInEvaluationResult;

import java.util.ArrayList;
import java.util.List;

public class EvaluationResultUtil {

    public static List<FillInEvaluationResult> convertEvaluationResultsToFillInEvaluationResultModel(List<EvaluationResult> results) {
        List<FillInEvaluationResult>  restResults = new ArrayList<>();
        for(EvaluationResult evaluationResult : results ){
            restResults.add(new FillInEvaluationResult(evaluationResult.getId(), evaluationResult.getEvaluation().getId(),
                    evaluationResult.getStudent(), evaluationResult.getSubCompetence(), evaluationResult.isPassed()));
        }
        return restResults;
    }
}
