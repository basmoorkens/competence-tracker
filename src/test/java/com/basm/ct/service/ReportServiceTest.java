package com.basm.ct.service;

import com.basm.ct.model.*;
import com.basm.ct.repository.EvaluationResultRepository;
import com.basm.ct.repository.ReportRepository;
import com.basm.ct.service.ReportService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ReportServiceTest {

    private List<EvaluationResult> results;

    private Competence comp1, comp2;

    private Student student1;

    @Mock
    private EvaluationResultRepository evaluationResultRepository;

    private Date startDate = new Date();
    private Date endDate =new Date();

    @InjectMocks
    private ReportService reportService;
    /*
    Setup the entire data model to generate a 2 competences with the first having 2 subcomps and the 2nd 3 subcomps
     */
    @Before
    public void initResults() {
        results = new ArrayList<>();
        SchoolClass schoolClass = new SchoolClass();
        schoolClass.setName("testClass");
        student1 = new Student();
        student1.setName("moorkens");
        student1.setFirstName("bas");
        schoolClass.setStudents(new ArrayList<>());
        schoolClass.getStudents().add(student1);
        schoolClass.linkUnlinkedStudents();
        comp1 = new Competence();
        comp1.setName("Comp1");
        SubCompetence subComp1 = new SubCompetence();
        subComp1.setName("subComp1");
        subComp1.setWeight(1);
        SubCompetence subComp2 = new SubCompetence();
        subComp2.setName("subComp2");
        subComp2.setWeight(2);
        comp1.addSubCompetence(subComp1);
        comp1.addSubCompetence(subComp2);
        comp1.linkUnlinkedSubCompetences();
        comp2 = new Competence();
        comp2.setName("comp2");
        SubCompetence sub3 = new SubCompetence();
        sub3.setName("subComp3");
        sub3.setWeight(1);
        SubCompetence sub4 = new SubCompetence();
        sub4.setName("subComp4");
        sub4.setWeight(1);
        SubCompetence sub5 = new SubCompetence();
        sub5.setName("subComp5");
        sub5.setWeight(2);
        comp2.addSubCompetence(sub3);
        comp2.addSubCompetence(sub4);
        comp2.addSubCompetence(sub5);
        comp2.linkUnlinkedSubCompetences();
        Evaluation eval1 = new Evaluation();
        eval1.setId(1L);
        eval1.setEvaluationDate(new Date());
        eval1.setName("Evaluation1");
        eval1.setSchoolClass(schoolClass);
        eval1.setSubCompetences(new HashSet<>());
        for(SubCompetence s : comp1.getSubCompetences()) {
            eval1.getSubCompetences().add(s);
        }
        for(SubCompetence s : comp2.getSubCompetences()) {
            eval1.getSubCompetences().add(s);
        }
        eval1.generateEvaluationResults();
        Evaluation eval2 = new Evaluation();
        eval2.setSubCompetences(new HashSet<>());
        eval2.setSchoolClass(schoolClass);
        eval2.setName("eval2");
        for(SubCompetence s : comp1.getSubCompetences()) {
            eval2.getSubCompetences().add(s);
        }
        for(SubCompetence s : comp2.getSubCompetences()) {
            eval2.getSubCompetences().add(s);
        }
        eval2.generateEvaluationResults();
        for(EvaluationResult r : eval1.getEvaluationResults()) {
            if(r.getSubCompetence().getName().equals("subComp1")) {
                r.setPassed(true);
            }
        }
        for(EvaluationResult r: eval2.getEvaluationResults()) {
            if(r.getSubCompetence().getName().equals("subComp5")) {
                r.setPassed(true);
            }
        }
        results = eval1.getEvaluationResults();
        results.addAll(eval2.getEvaluationResults());
    }

    @Test
    public void testGenerateReportForStudentAndStartAndEndDate() {
        when(evaluationResultRepository.getEvaluationResultsForStudentInPeriod(student1.getId(), startDate, endDate))
                .thenReturn(results);
        Report report = reportService.generateReportForStudentAndStartAndEndDate(student1, startDate, endDate);
        Assert.assertEquals("bas", report.getStudent().getFirstName());
        Assert.assertEquals(2, report.getCompetenceCalculationResults().size());
        for(CompetenceCalculationResult cr : report.getCompetenceCalculationResults()) {
            if(cr.getCompetenceName().equals("comp2")) {
                Assert.assertTrue(cr.isPassed());
                Assert.assertEquals(new Double(2), new Double(cr.getObtainedScore()));
                Assert.assertEquals(new Double(4), new Double(cr.getMaxScore()));
            }
            if(cr.getCompetenceName().equals("Comp1")) {
                Assert.assertFalse(cr.isPassed());
                Assert.assertEquals(new Double(3), new Double(cr.getMaxScore()));
                Assert.assertEquals(new Double(1), new Double(cr.getObtainedScore()));
            }
        }
    }

    @Test
    public void testgetResultsInMapBySubCompetenceName() {
        Map<String, List<EvaluationResult>> resultPerSubCompName = getMapBySubCompetence();
        Assert.assertEquals(2, resultPerSubCompName.keySet().size());
        Assert.assertTrue(resultPerSubCompName.containsKey("sb1"));
        Assert.assertTrue(resultPerSubCompName.containsKey("sb2"));
        Assert.assertTrue(resultPerSubCompName.get("sb1").size()==2);
        Assert.assertTrue(resultPerSubCompName.get("sb2").size()==3);
    }

    private Map<String, List<EvaluationResult>> getMapBySubCompetence() {
        comp1.setName("comp1");
        Student s = new Student();
        s.setName("testje");
        SubCompetence sb1 = new SubCompetence();
        sb1.setName("sb1");
        sb1.setWeight(1);
        SubCompetence sb2 = new SubCompetence();
        sb2.setName("sb2");
        sb2.setWeight(1);
        comp1.addSubCompetence(sb1);
        comp1.addSubCompetence(sb2);
        comp1.linkUnlinkedSubCompetences();
        EvaluationResult res1 = new EvaluationResult();
        res1.setSubCompetence(sb1);
        res1.setPassed(false);
        res1.setStudent(s);
        EvaluationResult res2 = new EvaluationResult();
        res2.setSubCompetence(sb2);
        res2.setPassed(true);
        res2.setStudent(s);
        EvaluationResult res3 = new EvaluationResult();
        res3.setSubCompetence(sb1);
        res3.setPassed(true);
        res3.setStudent(s);
        EvaluationResult res4 = new EvaluationResult();
        res4.setSubCompetence(sb2);
        res4.setPassed(false);
        res4.setStudent(s);
        EvaluationResult res5 = new EvaluationResult();
        res5.setSubCompetence(sb2);
        res5.setPassed(false);
        res5.setStudent(s);
        Evaluation eval = new Evaluation();
        Evaluation eval2 = new Evaluation();
        Evaluation eval3 = new Evaluation();
        eval.setName("eval1");
        eval2.setName("eval2");
        eval3.setName("eval3");
        eval.addEvaluationResult(res1);
        eval.addEvaluationResult(res2);
        eval2.addEvaluationResult(res3);
        eval2.addEvaluationResult(res4);
        eval3.addEvaluationResult(res5);

        return reportService.getResultsInMapBySubCompetenceName(Arrays.asList(res1, res2, res3,res4,res5));
    }

    @Test
    public void testcalculateSubCompetences() {
        Map<String, List<SubCompetenceCalculationResult>> results = reportService.calculateSubCompetences(getMapBySubCompetence());
        Assert.assertNotNull(results);
        Assert.assertEquals(2, results.get("comp1").size());
        Assert.assertTrue(results.get("comp1").get(1).isPassed());
        Assert.assertFalse(results.get("comp1").get(0).isPassed());
    }

    @Test
    public void testGenerateCompetenceCalcResult() {
        Map<String, List<SubCompetenceCalculationResult>> input = reportService.calculateSubCompetences(getMapBySubCompetence());
        CompetenceCalculationResult result = reportService.generateCompetenceCalculationResult(input, "comp1");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPassed());
        Assert.assertEquals(new Double(2), new Double(result.getMaxScore()));
        Assert.assertEquals(new Double(1), new Double(result.getObtainedScore()));
        Assert.assertEquals(new Double(2), new Double(result.getSubCompetenceCalculationResults().size()));
    }

    private Map<String, List<EvaluationResult>> getMultiCompMapBySubCompetence() {
        Competence comp2 = new Competence();
        comp2.setName("comp2");
        Competence comp1 = new Competence();
        comp1.setName("comp1");
        Student s = new Student();
        s.setName("testje");
        SubCompetence sb1 = new SubCompetence();
        sb1.setName("sb1");
        sb1.setWeight(1);
        SubCompetence sb2 = new SubCompetence();
        sb2.setName("sb2");
        sb2.setWeight(1);
        comp1.addSubCompetence(sb1);
        comp1.addSubCompetence(sb2);
        comp1.linkUnlinkedSubCompetences();
        SubCompetence sb3 = new SubCompetence();
        sb3.setName("sb3");
        sb3.setWeight(2);
        SubCompetence sb4 = new SubCompetence();
        sb4.setName("sb4");
        sb4.setWeight(1);
        SubCompetence sb5 = new SubCompetence();
        sb5.setName("sb5");
        sb5.setWeight(3);
        comp2.addSubCompetence(sb3);
        comp2.addSubCompetence(sb4);
        comp2.addSubCompetence(sb5);
        comp2.linkUnlinkedSubCompetences();
        EvaluationResult res1 = new EvaluationResult();
        res1.setSubCompetence(sb1);
        res1.setPassed(false);
        res1.setStudent(s);
        EvaluationResult res2 = new EvaluationResult();
        res2.setSubCompetence(sb2);
        res2.setPassed(true);
        res2.setStudent(s);
        EvaluationResult res3 = new EvaluationResult();
        res3.setSubCompetence(sb1);
        res3.setPassed(true);
        res3.setStudent(s);
        EvaluationResult res4 = new EvaluationResult();
        res4.setSubCompetence(sb2);
        res4.setPassed(false);
        res4.setStudent(s);
        EvaluationResult res5 = new EvaluationResult();
        res5.setSubCompetence(sb2);
        res5.setPassed(false);
        res5.setStudent(s);
        EvaluationResult res6 = new EvaluationResult();
        res6.setSubCompetence(sb3);
        res6.setPassed(false);
        res6.setStudent(s);
        EvaluationResult res7 = new EvaluationResult();
        res7.setSubCompetence(sb4);
        res7.setPassed(false);
        res7.setStudent(s);
        EvaluationResult res8 = new EvaluationResult();
        res8.setSubCompetence(sb5);
        res8.setPassed(true);
        res8.setStudent(s);
        EvaluationResult res9 = new EvaluationResult();
        res9.setSubCompetence(sb3);
        res9.setPassed(true);
        res9.setStudent(s);
        EvaluationResult res10 = new EvaluationResult();
        res10.setSubCompetence(sb3);
        res10.setPassed(false);
        res10.setStudent(s);
        Evaluation eval = new Evaluation();
        Evaluation eval2 = new Evaluation();
        Evaluation eval3 = new Evaluation();
        eval.setName("eval1");
        eval2.setName("eval2");
        eval3.setName("eval3");
        eval.addEvaluationResult(res1);
        eval.addEvaluationResult(res2);
        eval2.addEvaluationResult(res3);
        eval2.addEvaluationResult(res4);
        eval3.addEvaluationResult(res5);
        eval.addEvaluationResult(res6);
        eval2.addEvaluationResult(res7);
        eval3.addEvaluationResult(res8);
        eval.addEvaluationResult(res9);
        eval2.addEvaluationResult(res10);

        return reportService.getResultsInMapBySubCompetenceName(Arrays.asList(res1, res2, res3,res4,res5,res6, res7,res8,res9, res10));
    }

    @Test
    public void testGenerateCompetenceCalcResultMultipleCompetences() {
        Map<String, List<SubCompetenceCalculationResult>> input = reportService.calculateSubCompetences(getMultiCompMapBySubCompetence());
        CompetenceCalculationResult result = reportService.generateCompetenceCalculationResult(input, "comp1");
        Assert.assertNotNull(result);
        Assert.assertTrue(result.isPassed());
        Assert.assertEquals(new Double(2), new Double(result.getMaxScore()));
        Assert.assertEquals(new Double(1), new Double(result.getObtainedScore()));
        Assert.assertEquals(new Double(2), new Double(result.getSubCompetenceCalculationResults().size()));
        CompetenceCalculationResult result2 = reportService.generateCompetenceCalculationResult(input, "comp2");
        Assert.assertNotNull(result2);
        Assert.assertEquals(new Double(6), new Double(result2.getMaxScore()));
        Assert.assertEquals(new Double(3), new Double(result2.getObtainedScore()));
        Assert.assertEquals(new Double(1), new Double(result2.getSubCompetenceCalculationResults().get(0).getSubCompetenceWeight()));
        Assert.assertFalse(result2.getSubCompetenceCalculationResults().get(0).isPassed());
        Assert.assertEquals(new Double(2), new Double(result2.getSubCompetenceCalculationResults().get(1).getSubCompetenceWeight()));
        Assert.assertFalse(result2.getSubCompetenceCalculationResults().get(1).isPassed());
        Assert.assertEquals(new Double(3), new Double(result2.getSubCompetenceCalculationResults().get(2).getSubCompetenceWeight()));
        Assert.assertTrue(result2.getSubCompetenceCalculationResults().get(2).isPassed());
        Assert.assertTrue(result2.isPassed());
    }
}
