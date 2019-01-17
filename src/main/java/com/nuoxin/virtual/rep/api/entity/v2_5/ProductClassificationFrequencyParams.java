package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 产品下医生分型拜访频次设置
 * @author tiancun
 * @date 2019-01-04
 */
@Data
public class ProductClassificationFrequencyParams implements Serializable {
    private static final long serialVersionUID = 8374722716301981240L;


    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "分型ID")
    private Long classificationId;

    @ApiModelProperty(value = "医生潜力，3高,2中,1低,-1未知")
    private Integer potential;

    @ApiModelProperty(value = "拜访频次，单位工作日")
    private Integer visitFrequency;

    @ApiModelProperty(value = "批次，标识同时新增和修改的")
    private String batchNo;
}
