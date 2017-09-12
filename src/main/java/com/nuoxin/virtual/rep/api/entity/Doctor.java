package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "virtual_doctor")
public class Doctor extends IdEntity {

    private static final long serialVersionUID = 4739090689831737455L;

    @Column(name = "eapp_id")
    private Long eappId;
    @Column(name = "master_data_id")
    private Long masterDateId;
    @Column(name = "name")
    private String name;
    @Column(name = "hospital_id")
    private Long hospitalId;
    @Column(name = "hospital_name")
    private String hospitalName;
    @Column(name = "hospital_levle")
    private String hospitalLevle;
    @Column(name = "province")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "department")
    private String department;
    @Column(name = "mobile")
    private String mobile;
    @Column(name = "doctor_levle")
    private String doctorLevle;
    @Column(name = "client_levle")
    private String clientLevle;
    @Column(name = "create_time")
    private Date createTime;

    public Long getEappId() {
        return eappId;
    }

    public void setEappId(Long eappId) {
        this.eappId = eappId;
    }

    public Long getMasterDateId() {
        return masterDateId;
    }

    public void setMasterDateId(Long masterDateId) {
        this.masterDateId = masterDateId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(Long hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getHospitalLevle() {
        return hospitalLevle;
    }

    public void setHospitalLevle(String hospitalLevle) {
        this.hospitalLevle = hospitalLevle;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDoctorLevle() {
        return doctorLevle;
    }

    public void setDoctorLevle(String doctorLevle) {
        this.doctorLevle = doctorLevle;
    }

    public String getClientLevle() {
        return clientLevle;
    }

    public void setClientLevle(String clientLevle) {
        this.clientLevle = clientLevle;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
