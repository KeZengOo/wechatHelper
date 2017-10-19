package com.nuoxin.virtual.rep.api.web.controller.response.month_target;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "跟进类型")
public class FollowUpResponseBean implements Serializable{
    private static final long serialVersionUID = -7566608680238511397L;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "某一个跟进类型")
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
