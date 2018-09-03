package com.nuoxin.virtual.rep.api.web.controller.request.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "产品请求类")
public class ProductRequestBean implements Serializable{
    private static final long serialVersionUID = 7146647750763249468L;


    @ApiModelProperty(value = "销售id,默认取登录的，如果前端传就用前端的")
    private Long drugUserId;

    @ApiModelProperty(value = "前端不用传")
    private String leaderPath;

    public Long getDrugUserId() {
        return drugUserId;
    }

    public void setDrugUserId(Long drugUserId) {
        this.drugUserId = drugUserId;
    }

    public String getLeaderPath() {
        return leaderPath;
    }

    public void setLeaderPath(String leaderPath) {
        this.leaderPath = leaderPath;
    }
}
