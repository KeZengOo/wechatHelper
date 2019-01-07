package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 产品医生分型频次返回数据
 * @author tiancun
 * @date 2019-01-03
 */
@Data
@ApiModel(value = "产品医生分型频次返回数据")
public class ProductClassificationFrequencyResponseBean implements Serializable {
    private static final long serialVersionUID = -8708476663633656205L;

    @ApiModelProperty(value = "批次ID")
    private String batchNo;

    @ApiModelProperty(value = "潜力，3高,2中,1低,其他未知")
    private Integer potential;

    @ApiModelProperty(value = "多个分型以逗号分开")
    private String classificationStr;

    @ApiModelProperty(value = "频次")
    private Integer frequency;

    @ApiModelProperty(value = "选中的分型")
    private List<ProductClassificationResponseBean> classificationList = new ArrayList<>();




}
