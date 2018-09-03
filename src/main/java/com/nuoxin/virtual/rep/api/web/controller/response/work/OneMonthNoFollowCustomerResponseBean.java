package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "一个月未跟进客户返回数据")
public class OneMonthNoFollowCustomerResponseBean implements Serializable{
    private static final long serialVersionUID = -97926083517530917L;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医生手机号")
    private String doctorTel;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医生科室")
    private String depart;

    @ApiModelProperty(value = "客户级别")
    private String level;

    @ApiModelProperty(value = "连续多少天未跟进")
    private Integer diffDays;

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

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getDiffDays() {
        return diffDays;
    }

    public void setDiffDays(Integer diffDays) {
        this.diffDays = diffDays;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }


    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }
}
