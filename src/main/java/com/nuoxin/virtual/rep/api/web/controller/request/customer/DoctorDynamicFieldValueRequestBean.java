package com.nuoxin.virtual.rep.api.web.controller.request.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/22
 */
@ApiModel(value = "医生动态添加的字段的值")
public class DoctorDynamicFieldValueRequestBean implements Serializable{
    private static final long serialVersionUID = 507143997666787926L;

    @ApiModelProperty(value = "动态字段的id")
    private Long dynamicFieldId;

    @ApiModelProperty(value = "动态字段的值")
    private String dynamicFieldValue;


    public Long getDynamicFieldId() {
        return dynamicFieldId;
    }

    public void setDynamicFieldId(Long dynamicFieldId) {
        this.dynamicFieldId = dynamicFieldId;
    }

    public String getDynamicFieldValue() {
        return dynamicFieldValue;
    }

    public void setDynamicFieldValue(String dynamicFieldValue) {
        this.dynamicFieldValue = dynamicFieldValue;
    }
}
