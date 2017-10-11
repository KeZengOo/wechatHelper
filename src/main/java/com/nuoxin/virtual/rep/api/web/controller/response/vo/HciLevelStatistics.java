package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Created by guanliyuan on 17/8/4.
 */
public class HciLevelStatistics implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "统计数量")
    private Integer count;

    @ApiModelProperty(value = "医院级别")
    private Integer medicalGrade;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getMedicalGrade() {
        return medicalGrade;
    }

    public void setMedicalGrade(Integer medicalGrade) {
        this.medicalGrade = medicalGrade;
    }
}
