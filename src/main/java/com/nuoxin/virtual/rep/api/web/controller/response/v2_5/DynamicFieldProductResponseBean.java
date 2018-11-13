package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品设置产品信息
 * @author tiancun
 * @date 2018-09-18
 */
@ApiModel(value = "产品设置产品信息")
@Data
public class DynamicFieldProductResponseBean implements Serializable {
    private static final long serialVersionUID = 1682020160533345382L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;

    @ApiModelProperty(value = "更新人")
    private String menderName;

    @ApiModelProperty(value = "更新时间")
    private String updateTime;

    @ApiModelProperty(value = "拜访结果初始化状态 1已初始化 2未初始化")
    private Integer visitStatus;


}
