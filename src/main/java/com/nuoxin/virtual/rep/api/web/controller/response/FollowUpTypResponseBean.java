package com.nuoxin.virtual.rep.api.web.controller.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.Column;
import java.io.Serializable;

/**
 * Created by fenggang on 10/17/17.
 */
@ApiModel
public class FollowUpTypResponseBean implements Serializable {
    private static final long serialVersionUID = 3296329134971454515L;

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "产品id")
    private Long productId;
    @ApiModelProperty(value = "类型")
    private String type;
    @ApiModelProperty(value = "产品名")
    private String productName;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }
}
