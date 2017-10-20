package com.nuoxin.virtual.rep.api.web.controller.response.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 *
 * Create by tiancun on 2017/9/27
 */
@ApiModel(value = "医生的动态字段及值")
public class DoctorDymamicFieldValueResponseBean implements Serializable{
    private static final long serialVersionUID = 6656422391172399317L;

    @ApiModelProperty(value = "动态字段的id")
    private Long dynamicFieldId;

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;

    @ApiModelProperty(value = "字段类型")
    private Integer type;

    @ApiModelProperty(value = "下拉框类型字段的值，多个以英文逗号分开")
    private String fieldTypeValue;


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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFieldTypeValue() {
        return fieldTypeValue;
    }

    public void setFieldTypeValue(String fieldTypeValue) {
        this.fieldTypeValue = fieldTypeValue;
    }


    public Long getDynamicFieldId() {
        return dynamicFieldId;
    }

    public void setDynamicFieldId(Long dynamicFieldId) {
        this.dynamicFieldId = dynamicFieldId;
    }
}
