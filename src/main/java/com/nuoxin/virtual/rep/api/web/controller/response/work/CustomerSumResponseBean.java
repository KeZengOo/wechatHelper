package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel
public class CustomerSumResponseBean implements Serializable{
    private static final long serialVersionUID = 3065337414373938389L;

    @ApiModelProperty(value = "等级总数")
    private Integer typeSum;

    @ApiModelProperty(value = "等级类型")
    private String type;

    public Integer getTypeSum() {
        return typeSum;
    }

    public void setTypeSum(Integer typeSum) {
        this.typeSum = typeSum;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
