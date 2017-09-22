package com.nuoxin.virtual.rep.api.web.controller.request.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/22
 */
@ApiModel(value = "客户设置")
public class DoctorDynamicFieldRequestBean implements Serializable{
    private static final long serialVersionUID = 4172100860423852688L;


    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段别名或者英文名称")
    private String alias;


    @ApiModelProperty(value = "字段类型，1是文本，2是下拉框")
    private Integer type;

    @ApiModelProperty(value = "下拉框的值")
    private String value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
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
}
