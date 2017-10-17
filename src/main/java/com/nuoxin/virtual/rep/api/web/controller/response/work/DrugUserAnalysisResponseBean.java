package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "坐席分析返回数据")
public class DrugUserAnalysisResponseBean implements Serializable{
    private static final long serialVersionUID = -645082110497219239L;

    @ApiModelProperty(value = "坐席名称")
    private String drugUserName;

    @ApiModelProperty(value = "返回的最大最小值")
    private String num;


    public String getDrugUserName() {
        return drugUserName;
    }

    public void setDrugUserName(String drugUserName) {
        this.drugUserName = drugUserName;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }
}
