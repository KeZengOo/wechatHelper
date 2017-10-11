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

    @ApiModelProperty(value = "字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;


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
}
