package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by fenggang on 9/11/17.
 */
@Entity
@Table(name = "doctor")
public class Doctor extends IdEntity {

    private static final long serialVersionUID = 4739090689831737455L;

    @Transient
    private Long eappId;
    @Column(name = "email")
    private String email;
    @Column(name = "name")
    private String name;
    @Column(name = "hospital_id")
    private Long hospitalId;
    @Column(name = "hospital_name")
    private String hospitalName;
    @Column(name = "provice")
    private String province;
    @Column(name = "city")
    private String city;
    @Column(name = "depart")
    private String department;
    @Column(name = "telephone")
    private String mobile;
    @Column(name = "positions")
    private String doctorLevel;
    @Column(name = "create_time")
    private Date createTime;
    @Column(name = "status")
    private Integer status;
    @Column(name = "master_data_id")
    private Integer masterDataId;
    @Column(name = "sex")
    private Integer sex;


    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH},fetch = FetchType.EAGER)
    @JoinColumn(name = "virtual_doctor_id")
//    @Transient
    private DoctorVirtual doctorVirtual;

    public Long getEappId() {
        return eappId;
    }

    public void setEappId(Long eappId) {
        this.eappId = eappId;
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

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public DoctorVirtual getDoctorVirtual() {
        return doctorVirtual;
    }

    public void setDoctorVirtual(DoctorVirtual doctorVirtual) {
        this.doctorVirtual = doctorVirtual;
    }

    public Integer getMasterDataId() {
        return masterDataId;
    }

    public void setMasterDataId(Integer masterDataId) {
        this.masterDataId = masterDataId;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }
}
