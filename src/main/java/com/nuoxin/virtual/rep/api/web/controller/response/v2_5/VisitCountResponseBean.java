package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 指定日期拜访人数
 * @author tiancun
 * @date 2018-12-29
 */
@Data
@ApiModel(value = "指定日期拜访人数")
public class VisitCountResponseBean implements Serializable {
    private static final long serialVersionUID = -3350869252050300708L;

    @ApiModelProperty(value = "日期")
    private String date;

    @ApiModelProperty(value = "人数")
    private Integer count;

}
