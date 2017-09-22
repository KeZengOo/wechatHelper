package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/9/22
 */
@Entity
@Table(name = "virtual_doctor_dynamic_field_value")
public class DoctorDynamicFieldValue extends IdEntity{

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "dynamic_field_id")
    private Long dynamicFieldId;

    @Column(name = "dynamic_field_value")
    private String dynamicFieldValue;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;


    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getDynamicFieldId() {
        return dynamicFieldId;
    }

    public void setDynamicFieldId(Long dynamicFieldId) {
        this.dynamicFieldId = dynamicFieldId;
    }

    public String getDynamicFieldValue() {
        return dynamicFieldValue;
    }

    public void setDynamicFieldValue(String dynamicFieldValue) {
        this.dynamicFieldValue = dynamicFieldValue;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}
