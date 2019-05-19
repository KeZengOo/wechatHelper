package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 电话拜访统计
 * @author tiancun
 * @date 2019-05-17
 */
@Data
@ApiModel(value = "电话拜访统计")
public class CallVisitStatisticsResponse implements Serializable {
    private static final long serialVersionUID = 1147028329872512751L;

    @ApiModelProperty(value = "外呼次数")
    private Integer callCount;

    @ApiModelProperty(value = "接通次数")
    private Integer connectCallCount;

    @ApiModelProperty(value = "未接通次数")
    private Integer unConnectCallCount;

    @ApiModelProperty(value = "总的通话时长")
    private String totalCallTime;

    @ApiModelProperty(value = "总的通话秒数")
    private Long totalCallSecond;



}
