package com.nuoxin.virtual.rep.api.web.controller.response.product;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "产品返回实体")
public class ProductResponseBean implements Serializable{
    private static final long serialVersionUID = 8932043450471599020L;

    @ApiModelProperty(value = "产品id")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
