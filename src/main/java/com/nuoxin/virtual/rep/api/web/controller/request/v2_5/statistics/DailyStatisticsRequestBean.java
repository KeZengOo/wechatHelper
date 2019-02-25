package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 日报请求参数
 * @author tiancun
 * @date 2019-02-25
 */
@Data
@ApiModel(value = "日报请求参数")
public class DailyStatisticsRequestBean implements Serializable {
    private static final long serialVersionUID = -4842119119711492880L;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID")
    private Long drugUserId;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;

}
