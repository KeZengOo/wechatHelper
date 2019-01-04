package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品医生分型设置返回数据
 * @author tiancun
 * @date 2019-01-03
 */
@Data
@ApiModel(value = "产品医生分型列表返回数据，用于列表查询")
public class ProductClassificationResponseBean implements Serializable {
    private static final long serialVersionUID = -8708476663633656205L;

    @ApiModelProperty(value = "ID")
    private Long id;

    @ApiModelProperty(value = "字段的值")
    private String fieldValue;




}
