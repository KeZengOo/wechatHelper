package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "坐席分析返回数据")
public class DrugUserAnalysisListResponseBean implements Serializable{
    private static final long serialVersionUID = -645082110497219239L;

    @ApiModelProperty(value = "总通话时间最短")
    private List<DrugUserAnalysisResponseBean> minTotalCallTimeList;

    @ApiModelProperty(value = "平均通话时间最短")
    private List<DrugUserAnalysisResponseBean> minAvgCallTimeList;

    @ApiModelProperty(value = "电话数量最少")
    private List<DrugUserAnalysisResponseBean> minTotalCallCountList;

    @ApiModelProperty(value = "电话覆盖数量最少")
    private List<DrugUserAnalysisResponseBean> minCallCoveredCount;

    private List<DrugUserAnalysisResponseBean> minTotal;

}
