package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

public class HciAlias implements Serializable{

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主数据医院别名")
    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
}
