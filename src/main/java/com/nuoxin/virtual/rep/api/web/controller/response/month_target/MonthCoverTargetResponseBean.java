package com.nuoxin.virtual.rep.api.web.controller.response.month_target;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/19
 */
@ApiModel(value = "月覆盖目标")
public class MonthCoverTargetResponseBean implements Serializable{
    private static final long serialVersionUID = 9077835306707598996L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "客户等级")
    private String level;

    @ApiModelProperty(value = "月覆盖目标")
    private Integer monthCovered;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
