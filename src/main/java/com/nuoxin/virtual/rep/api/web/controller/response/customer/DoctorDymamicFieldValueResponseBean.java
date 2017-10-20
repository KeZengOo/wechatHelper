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
    private Long id;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;

    @ApiModelProperty(value = "字段类型")
    private Integer type;

    @ApiModelProperty(value = "下拉框类型字段的值，多个以英文逗号分开")
    private String value;



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

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
