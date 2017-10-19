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

    @ApiModelProperty(value = "跟进类型，多个以英文逗号分开")
    private String types;


    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getTypes() {
        return types;
    }

    public void setTypes(String types) {
        this.types = types;
    }



}
