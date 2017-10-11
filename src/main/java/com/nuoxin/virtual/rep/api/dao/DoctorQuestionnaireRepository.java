package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorQuestionnaireRepository extends JpaRepository<DoctorQuestionnaire,Long>,JpaSpecificationExecutor<DoctorQuestionnaire> {

    List<DoctorQuestionnaire> findByCallId(Long callId);
}
