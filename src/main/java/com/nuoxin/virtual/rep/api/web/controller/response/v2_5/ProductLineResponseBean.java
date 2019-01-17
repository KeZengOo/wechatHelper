package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-10-02
 */
@Data
public class ProductLineResponseBean implements Serializable {
    private static final long serialVersionUID = -5533018006271716070L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

}
