package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 10/18/17.
 */
@Entity
@Table(name = "virtual_contact_plan")
public class ContactPlan extends IdEntity {

    private static final long serialVersionUID = 1164497247657893019L;

    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "content")
    private String content;
    @Column(name = "doctor_id")
    private Long doctorId;
    @Column(name = "drug_user_id")
    private Long drugUserId;
    @Column(name = "status")
    private Integer status;
    @Column(name = "date_time")
    private Date dateTime;
    @Column(name = "finish_time")
    private Date finishTime;

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }
}
