package com.nuoxin.virtual.rep.api.web.controller.response.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/19
 */
@ApiModel(value = "医生基本信息返回数据")
public class DoctorBasicInfoResponseBean implements Serializable{
    private static final long serialVersionUID = 5527300879361408851L;

    @ApiModelProperty(value = "动态字段的值")
    private Long ddfvId;

    @ApiModelProperty(value = "字段id")
    private String fieldId;

    @ApiModelProperty(value = "字段名称")
    private String field;

    @ApiModelProperty(value = "对应下拉框的值，只有下拉框类型字段才会有的值")
    private String fieldValue;

    @ApiModelProperty(value = "添加的字段的值")
    private String value;

    @ApiModelProperty(value = "字段类型")
    private Integer type;

    @ApiModelProperty(value = "分类，例如1是基本信息，2医生的处方信息")
    private Integer classification;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getClassification() {
        return classification;
    }

    public void setClassification(Integer classification) {
        this.classification = classification;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }


    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }


    public Long getDdfvId() {
        return ddfvId;
    }

    public void setDdfvId(Long ddfvId) {
        this.ddfvId = ddfvId;
    }


    public String getFieldId() {
        return fieldId;
    }

    public void setFieldId(String fieldId) {
        this.fieldId = fieldId;
    }
}
