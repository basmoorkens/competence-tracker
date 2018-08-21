package com.basm.ct.repository;

import com.basm.ct.model.EvaluationResult;
import com.basm.ct.model.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {

    @Query("SELECT s.evaluationResults FROM Student s where s.id = :studentId")
    List<EvaluationResult> getEvaluationResultsForStudentId(@Param("studentId") final long studentId);
}
