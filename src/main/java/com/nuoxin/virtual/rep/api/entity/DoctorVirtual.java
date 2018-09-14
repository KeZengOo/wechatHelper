package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fenggang on 10/13/17.
 */
@Entity
@Table(name = "doctor_virtual")
public class DoctorVirtual extends IdEntity {
    private static final long serialVersionUID = -7010984054013700843L;

    @Column(name = "drug_user_ids")
    private String drugUserIds;
    @Column(name = "master_data_id")
    private Long masterDateId;
    @Column(name = "client_level")
    private String clientLevel;
    @Column(name = "hospital_level")
    private String hospitalLevel;
    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "doctor_id")
    private Long doctorId;

    @Column(name = "product_id")
    private Long productId;

    public String getDrugUserIds() {
        return drugUserIds;
    }

    public void setDrugUserIds(String drugUserIds) {
        this.drugUserIds = drugUserIds;
    }

    public Long getMasterDateId() {
        return masterDateId;
    }

    public void setMasterDateId(Long masterDateId) {
        this.masterDateId = masterDateId;
    }

    public String getClientLevel() {
        return clientLevel;
    }

    public void setClientLevel(String clientLevel) {
        this.clientLevel = clientLevel;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
