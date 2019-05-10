package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * V3.0日报请求参数
 * @author tiancun
 * @date 2019-05-09
 */
@Data
@ApiModel(value = "V3.0日报请求参数")
public class DailyReportRequest implements Serializable {
    private static final long serialVersionUID = -8473359742664477431L;

    @ApiModelProperty(value = "产品ID列表")
    private List<Long> productIdList;

    @ApiModelProperty(value = "代表ID列表")
    private List<Long> drugUserIdList;

    @ApiModelProperty(value = "开始时间")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    private Date endTime;



}
