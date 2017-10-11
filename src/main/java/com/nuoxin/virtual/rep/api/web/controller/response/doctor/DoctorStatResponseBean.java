package com.nuoxin.virtual.rep.api.web.controller.response.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by fenggang on 9/12/17.
 */
@ApiModel
public class DoctorStatResponseBean implements Serializable {

    private static final long serialVersionUID = 4054761929458613106L;

    @ApiModelProperty(value = "医生数量")
    private Integer doctorNum = 0;
    @ApiModelProperty(value = "医院数量")
    private Integer hospitalNum = 0;

    public Integer getDoctorNum() {
        return doctorNum;
    }

    public void setDoctorNum(Integer doctorNum) {
        this.doctorNum = doctorNum;
    }

    public Integer getHospitalNum() {
        return hospitalNum;
    }

    public void setHospitalNum(Integer hospitalNum) {
        this.hospitalNum = hospitalNum;
    }
}
