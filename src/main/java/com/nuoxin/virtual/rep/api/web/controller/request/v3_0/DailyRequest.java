package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 日报请求参数
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "日报请求参数")
@Data
public class DailyRequest implements Serializable {
    private static final long serialVersionUID = 6822651443137818580L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;

    @ApiModelProperty(value = "开始时间")
    private String startTime;

    @ApiModelProperty(value = "结束时间")
    private String endTime;




}