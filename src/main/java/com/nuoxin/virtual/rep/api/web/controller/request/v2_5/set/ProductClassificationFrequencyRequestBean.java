package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品分型拜访频次请求参数
 * @author tiancun
 * @date 2019-01-04
 */
@Data
@ApiModel(value = "产品分型拜访频次请求参数")
public class ProductClassificationFrequencyRequestBean implements Serializable {
    private static final long serialVersionUID = -5967723603124190461L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "分型")
    private List<String> classificationList;


    @ApiModelProperty(value = "医生潜力")
    private String potential;

    @ApiModelProperty(value = "拜访频次，单位工作日")
    private Integer visitFrequency;
}
