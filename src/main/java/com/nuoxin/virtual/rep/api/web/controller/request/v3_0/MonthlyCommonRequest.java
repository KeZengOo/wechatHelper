package com.nuoxin.virtual.rep.api.web.controller.request.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 月报公共请求参数
 * @author tiancun
 * @date 2019-06-12
 */
@ApiModel(value = "月报公共请求参数")
@Data
public class MonthlyCommonRequest implements Serializable {
    private static final long serialVersionUID = 4145284153450174146L;


    @ApiModelProperty(value = "产品ID")
    @NotNull(message = "产品ID不能为空")
    protected Long productId;

    @ApiModelProperty(value = "开始时间")
    @NotNull(message = "开始时间不能为空")
    private String startDate;

    @ApiModelProperty(value = "结束时间")
    @NotNull(message = "结束时间不能为空")
    private String endDate;



}
