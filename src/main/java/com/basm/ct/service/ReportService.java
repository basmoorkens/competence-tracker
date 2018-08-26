package com.basm.ct.service;

import com.basm.ct.model.*;
import com.basm.ct.repository.EvaluationResultRepository;
import com.basm.ct.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ReportService {

    @Autowired
    private EvaluationResultRepository evaluationResultRepository;

    @Autowired
    private ReportRepository reportRepository;

    public Report generateAndSaveReportForStudentAndStartAndEndDate(final Student student, final Date startDate, final Date endDate){
        Report report = generateReportForStudentAndStartAndEndDate(student, startDate, endDate);
        return reportRepository.save(report);
    }

    public Report generateReportForStudentAndStartAndEndDate(final Student student, final Date startDate, final Date endDate){
        Report report = createBasicReport(student, startDate, endDate);
        List<EvaluationResult> evaluationResultsForStudentInPeriod = evaluationResultRepository.getEvaluationResultsForStudentInPeriod(student.getId(), startDate, endDate);
        Map<String, List<EvaluationResult>> resultsBySubCompetence = getResultsInMapBySubCompetenceName(evaluationResultsForStudentInPeriod);

        Map<String, List<SubCompetenceCalculationResult>> subcompetenceResultsByCompetenceName = calculateSubCompetences(resultsBySubCompetence);
        for(String competenceName : subcompetenceResultsByCompetenceName.keySet()) {
            CompetenceCalculationResult competenceResult = generateCompetenceCalculationResult(subcompetenceResultsByCompetenceName, competenceName);
            report.addCompetenceCalculationResult(competenceResult);
        }
        return report;
    }

    protected CompetenceCalculationResult generateCompetenceCalculationResult(Map<String, List<SubCompetenceCalculationResult>> subcompetenceResultsByCompetenceName, String competenceName) {
        CompetenceCalculationResult competenceResult = new CompetenceCalculationResult();
        competenceResult.setCompetenceName(competenceName);
        competenceResult.setSubCompetenceCalculationResults(subcompetenceResultsByCompetenceName.get(competenceName));
        competenceResult.calculateCompetenceResults();
        return competenceResult;
    }

    /**
     * Convert the map with subcompetence name and list of results into a subcompetence a map with key competence name and value list of subcompetenc results.
     * @param resultsBySubCompetence
     * @return
     */
    protected Map<String, List<SubCompetenceCalculationResult>> calculateSubCompetences(Map<String, List<EvaluationResult>> resultsBySubCompetence) {
        Map<String, List<SubCompetenceCalculationResult>> resultMap = new HashMap<>();
        SubCompetenceCalculationResult calculationResult;
        for(String key : resultsBySubCompetence.keySet()) {
            calculationResult = calculateIndividualSubCompetence(resultsBySubCompetence, key);
            String competenceName = calculationResult.getCompetenceName();
            if(!resultMap.containsKey(competenceName)) {
                resultMap.put(competenceName, new ArrayList<>());
            }
            resultMap.get(competenceName).add(calculationResult);
        }
        return resultMap;
    }

    private SubCompetenceCalculationResult calculateIndividualSubCompetence(Map<String, List<EvaluationResult>> resultsBySubCompetence, String key) {
        SubCompetenceCalculationResult calculationResult;
        SubCompetenceCalculationLine calculationLine;
        calculationResult = new SubCompetenceCalculationResult();
        for(EvaluationResult evaluationResult : resultsBySubCompetence.get(key)) {
            calculationLine = new SubCompetenceCalculationLine();
            calculationLine.setEvaluationName(evaluationResult.getEvaluation().getName());
            calculationLine.setPassed(evaluationResult.isPassed());
            calculationResult.setSubCompetenceName(evaluationResult.getSubCompetence().getName());
            calculationResult.setCompetenceName(evaluationResult.getSubCompetence().getParentCompetenceName());
            calculationResult.addSubCompetenceCalculationLine(calculationLine);
            calculationResult.setSubCompetenceWeight(evaluationResult.getSubCompetence().getWeight());
        }
        calculationResult.calculateIfSubCompetenceIsPassed();
        return calculationResult;
    }

    protected Map<String, List<EvaluationResult>> getResultsInMapBySubCompetenceName(List<EvaluationResult> evaluationResultsForStudentInPeriod) {
        Map<String, List<EvaluationResult>> resultsBySubCompetence = new HashMap<>();
        for(EvaluationResult evaluationResult : evaluationResultsForStudentInPeriod) {
            if(!resultsBySubCompetence.containsKey(evaluationResult.getSubCompetence().getName())) {
                resultsBySubCompetence.put(evaluationResult.getSubCompetence().getName(), new ArrayList<>());
            }
            resultsBySubCompetence.get(evaluationResult.getSubCompetence().getName()).add(evaluationResult);
        }
        return resultsBySubCompetence;
    }

    private Report createBasicReport(final Student student, final Date startDate, final Date endDate){
        Report report = new Report();
        report.setStudent(student);
        report.setStartDate(startDate);
        report.setEndDate(endDate);
        return report;
    }

}
