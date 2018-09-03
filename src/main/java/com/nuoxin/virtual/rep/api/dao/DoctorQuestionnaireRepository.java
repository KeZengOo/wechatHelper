package com.nuoxin.virtual.rep.api.dao;

import com.nuoxin.virtual.rep.api.entity.DoctorQuestionnaire;
import com.nuoxin.virtual.rep.api.entity.Questionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by fenggang on 9/11/17.
 */
public interface DoctorQuestionnaireRepository extends JpaRepository<DoctorQuestionnaire,Long>,JpaSpecificationExecutor<DoctorQuestionnaire> {

    /**
     * 根据电话标识获取问卷
     * @param callId
     * @return
     */
    List<DoctorQuestionnaire> findByCallId(Long callId);

    /**
     * 获取医生答题信息
     * @param questionId
     * @param drugUserId
     * @param doctorId
     * @return
     */
    @Query("select d from DoctorQuestionnaire d where d.doctorId=:doctorId and d.questionId=:questionId and d.drugUserId=:drugUserId order by d.createTime desc")
    List<DoctorQuestionnaire> findByQuestionIdAndQuestionnaireId(@Param("questionId") Long questionId, @Param("drugUserId") Long drugUserId, @Param("doctorId") Long doctorId);
}
