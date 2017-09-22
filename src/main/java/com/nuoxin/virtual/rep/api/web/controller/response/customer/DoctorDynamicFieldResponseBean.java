package com.nuoxin.virtual.rep.api.web.controller.response.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/9/22
 */
@ApiModel(value = "返回的医生动态字段")
public class DoctorDynamicFieldResponseBean implements Serializable{
    private static final long serialVersionUID = 426766817698746896L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段的别名或者英文名称")
    private String alias;

    @ApiModelProperty(value = "字段类型，1是文本，2是下拉框")
    private Integer type;

    @ApiModelProperty(value = "下拉框的值")
    private String value;


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
