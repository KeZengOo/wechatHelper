package com.nuoxin.virtual.rep.api.web.controller.response.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

@ApiModel(value = "科室")
public class Dept implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "科室")
    private String dept;


    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }
}
