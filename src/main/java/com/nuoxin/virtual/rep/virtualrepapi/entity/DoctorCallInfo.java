package com.nuoxin.virtual.rep.virtualrepapi.entity;

import com.nuoxin.virtual.rep.virtualrepapi.common.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_doctor_call_info")
public class DoctorCallInfo extends IdEntity {

    //@Column(name = "virtual_doctor_id")
    @Transient
    private Long doctorId;
    @Column(name = "virtual_drug_user_id")
    private Long drugUserId;
    @Column(name = "sin_token")
    private String sinToken;
    @Column(name = "call_time")
    private Long callTime;
    @Column(name = "call_url")
    private String callUrl;
    @Column(name = "doctor_questionnaire_id")
    private Long questionnaireId;
    @Column(name = "remark")
    private String remark;
    @Column(name = "status")
    private String status;
    @Column(name = "create_time")
    private Date createTime;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.LAZY)
    @JoinColumn(name = "virtual_doctor_id")
    private Doctor doctor;

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

    public String getSinToken() {
        return sinToken;
    }

    public void setSinToken(String sinToken) {
        this.sinToken = sinToken;
    }

    public Long getCallTime() {
        return callTime;
    }

    public void setCallTime(Long callTime) {
        this.callTime = callTime;
    }

    public String getCallUrl() {
        return callUrl;
    }

    public void setCallUrl(String callUrl) {
        this.callUrl = callUrl;
    }

    public Long getQuestionnaireId() {
        return questionnaireId;
    }

    public void setQuestionnaireId(Long questionnaireId) {
        this.questionnaireId = questionnaireId;
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

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
