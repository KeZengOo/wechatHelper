package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/19/17.
 */
@Entity
@Table(name = "virtual_doctor_questionnaire")
public class DoctorQuestionnaire extends IdEntity {

    private static final long serialVersionUID = -1967687600941129293L;
    @Column(name = "virtual_doctor_id")
    private Long doctorId;
    @Column(name = "virtual_drug_user_id")
    private Long drugUserId;
    @Column(name = "virtual_questionnaire_id")
    private Long questionnaireId;
    @Column(name = "virtual_question_id")
    private Long questionId;
    @Column(name = "answer")
    private String answer;
    @Column(name = "remark")
    private String remark;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "call_id")
    private Long callId;

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCallId() {
        return callId;
    }

    public void setCallId(Long callId) {
        this.callId = callId;
    }
}
