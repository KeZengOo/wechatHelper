package com.nuoxin.virtual.rep.api.web.controller.request.month_target;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "跟进类型请求类")
public class FollowUpSetRequestBean implements Serializable{
    private static final long serialVersionUID = -3725559781677506297L;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "跟进类型")
    private String type;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
