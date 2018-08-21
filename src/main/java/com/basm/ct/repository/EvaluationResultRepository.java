package com.basm.ct.repository;


import com.basm.ct.model.EvaluationResult;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface EvaluationResultRepository extends CrudRepository<EvaluationResult, Long> {

    @Transactional
    @Modifying
    @Query("UPDATE  EvaluationResult ev SET ev.version = ev.version + 1, ev.passed = TRUE  WHERE ev.id in (:ids)")
    public  void setResultsToPassedForIds(@Param("ids") final List<Long> idList);

    @Transactional
    @Modifying
    @Query("UPDATE  EvaluationResult ev SET ev.version = ev.version + 1, ev.passed = FALSE WHERE ev.id in (:ids)")
    public  void setResultsToFailedForIds(@Param("ids") final List<Long> idList);

}
