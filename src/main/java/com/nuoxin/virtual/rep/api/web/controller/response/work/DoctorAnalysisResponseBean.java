package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "客户分析返回数据")
public class DoctorAnalysisResponseBean implements Serializable{
    private static final long serialVersionUID = -3728987692000460089L;

    @ApiModelProperty(value = "客户名称")
    private String doctorName="";

    @ApiModelProperty(value = "返回的最大最小值")
    private Integer num=0;


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
