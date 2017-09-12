package com.nuoxin.virtual.rep.api.web.controller.request.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class DoctorRequestBean implements Serializable {

    private static final long serialVersionUID = 3730618881184904557L;
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "客户等级")
    private String clientLevle;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医院级别")
    private String hospitalLevle;
    @ApiModelProperty(value = "科室")
    private String department;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "医生级别")
    private String doctorLevle;
    @ApiModelProperty(value = "教授")
    private String professor;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getClientLevle() {
        return clientLevle;
    }

    public void setClientLevle(String clientLevle) {
        this.clientLevle = clientLevle;
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

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

    public String getDoctorLevle() {
        return doctorLevle;
    }

    public void setDoctorLevle(String doctorLevle) {
        this.doctorLevle = doctorLevle;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
