package com.nuoxin.virtual.rep.api.web.controller.request.month_target;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "月覆盖目标设置请求类")
public class MonthCoverTargetSetRequestBean implements Serializable{
    private static final long serialVersionUID = 8786735471482918158L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "医生的等级")
    private String level;

    @ApiModelProperty(value = "月覆盖目标")
    private Integer monthCovered;



    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getMonthCovered() {
        return monthCovered;
    }

    public void setMonthCovered(Integer monthCovered) {
        this.monthCovered = monthCovered;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
