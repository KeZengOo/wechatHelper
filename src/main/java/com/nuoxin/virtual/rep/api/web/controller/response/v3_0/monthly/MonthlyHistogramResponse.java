package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 月报柱状图返回数据
 * @author tiancun
 * @date 2019-06-18
 */
@Data
@ApiModel(value = "月报柱状图返回数据")
public class MonthlyHistogramResponse implements Serializable {
    private static final long serialVersionUID = -5802209454618883715L;

    @ApiModelProperty(value = "横坐标")
    private List<String> abscissa;

    @ApiModelProperty(value = "纵坐标")
    private List<List<Integer>> ordinate;
}
