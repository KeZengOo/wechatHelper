package com.nuoxin.virtual.rep.api.web.controller.response.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class DoctorDetailsResponseBean implements Serializable {

    private static final long serialVersionUID = -7987399012710361095L;
    private Long doctorId;
    @ApiModelProperty(value = "营销id")
    private Long eappId;
    @ApiModelProperty(value = "主数据id")
    private Long masterDateId;
    @ApiModelProperty(value = "姓名")
    private String doctorName;
    @ApiModelProperty(value = "医院id")
    private Long hospitalId;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医院等级")
    private String hospitalLevle;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "科室")
    private String department;
    @ApiModelProperty(value = "电话")
    private String mobile;
    @ApiModelProperty(value = "医生等级")
    private String doctorLevle;
    @ApiModelProperty(value = "客户等级")
    private String clientLevle;

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

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
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
}
