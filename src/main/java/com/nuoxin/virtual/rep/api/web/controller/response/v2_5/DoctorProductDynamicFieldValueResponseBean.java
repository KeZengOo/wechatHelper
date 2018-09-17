package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 医生产品动态字段返回数据
 * @author tiancun
 * @date 2018-09-17
 */
@Data
@ApiModel(description = "医生产品动态字段返回数据")
public class DoctorProductDynamicFieldValueResponseBean extends DoctorBasicDynamicFieldValueResponseBean{
    private static final long serialVersionUID = -2926330131291303311L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;




}
