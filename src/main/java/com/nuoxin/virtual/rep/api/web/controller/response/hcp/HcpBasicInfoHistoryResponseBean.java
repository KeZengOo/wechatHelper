package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * @author tiancun
 */
@ApiModel(value = "基本信息修改历史")
public class HcpBasicInfoHistoryResponseBean implements Serializable{
    private static final long serialVersionUID = 4297902544221975021L;


    @ApiModelProperty(value = "之前字段名称")
    private String oldFieldName;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "之前字段的值")
    private String oldFieldValue;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;

    @ApiModelProperty(value = "销售姓名")
    private String drugUserName;


    @ApiModelProperty(value = "正确")
    private String correct;

    @ApiModelProperty(value = "修改时间")
    private String updateTime;


    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public String getCorrect() {
        return correct;
    }

    public void setCorrect(String correct) {
        this.correct = correct;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


    public String getOldFieldName() {
        return oldFieldName;
    }

    public void setOldFieldName(String oldFieldName) {
        this.oldFieldName = oldFieldName;
    }

    public String getOldFieldValue() {
        return oldFieldValue;
    }

    public void setOldFieldValue(String oldFieldValue) {
        this.oldFieldValue = oldFieldValue;
    }
}
