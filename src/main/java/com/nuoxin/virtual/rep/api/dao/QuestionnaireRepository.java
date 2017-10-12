package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.Question;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by fenggang on 9/11/17.
 */
public interface QuestionnaireRepository extends JpaRepository<Questionnaire,Long>,JpaSpecificationExecutor<Questionnaire> {

    @Modifying
    @Query("update Questionnaire q set q.delFlag=1  where q.id=:id")
    void updateDelFlag(@Param("id") Long id);
}
