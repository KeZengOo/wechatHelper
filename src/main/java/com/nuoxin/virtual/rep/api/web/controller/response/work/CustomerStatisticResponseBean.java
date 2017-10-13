package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "客户统计")
public class CustomerStatisticResponseBean implements Serializable{
    private static final long serialVersionUID = 147246881009750323L;

    @ApiModelProperty(value = "客户类型")
    private String type;

    @ApiModelProperty(value = "客户类型百分比")
    private String percent;


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPercent() {
        return percent;
    }

    public void setPercent(String percent) {
        this.percent = percent;
    }
}
