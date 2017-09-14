package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_doctor_call_info")
public class DoctorCallInfo extends IdEntity {

    private static final long serialVersionUID = 4671164567577414946L;
    //@Column(name = "virtual_doctor_id")
    @Transient
    private Long doctorId;
    @Column(name = "type")
    private Integer type;
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
    private Integer status;
    @Column(name = "status_name")
    private String statusName;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "mobile")
    private String mobile;

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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
