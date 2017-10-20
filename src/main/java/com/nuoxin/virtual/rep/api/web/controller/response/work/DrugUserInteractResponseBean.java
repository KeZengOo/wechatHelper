package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/19
 */
@ApiModel(value = "销售互动返回")
public class DrugUserInteractResponseBean implements Serializable{
    private static final long serialVersionUID = -6388110600109245756L;

    @ApiModelProperty(value = "销售id")
    private Long drugUserId;

    @ApiModelProperty(value = "销售姓名")
    private String drugUserName;

    @ApiModelProperty(value = "通话总时长，单位秒")
    private Integer callSumTime;

    @ApiModelProperty(value = "电话覆盖人数")
    private Integer callNum;

    @ApiModelProperty(value = "平均通话时长，单位秒")
    private Integer avgCallTime;


    public Integer getCallSumTime() {
        return callSumTime;
    }

    public void setCallSumTime(Integer callSumTime) {
        this.callSumTime = callSumTime;
    }

    public Integer getCallNum() {
        return callNum;
    }

    public void setCallNum(Integer callNum) {
        this.callNum = callNum;
    }

    public Integer getAvgCallTime() {
        return avgCallTime;
    }

    public void setAvgCallTime(Integer avgCallTime) {
        this.avgCallTime = avgCallTime;
    }

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }
}
