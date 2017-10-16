package com.nuoxin.virtual.rep.api.web.controller.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "工作台请求类")
public class WorkStationRequestBean implements Serializable{
    private static final long serialVersionUID = 3083177023027501328L;

    @ApiModelProperty(value = "销售id，前端不用传")
    private Long drugUserId;

    @ApiModelProperty(value = "模糊查询销售，前端不用传")
    private String leaderPath;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }
}
