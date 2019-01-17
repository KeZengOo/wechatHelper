package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-01-07
 */
@Data
@ApiModel(value = "拜访频次设置请求参数")
public class ProductVisitFrequencyRequestBean implements Serializable {
    private static final long serialVersionUID = 2367551107528435198L;

    @ApiModelProperty(value = "产品ID")
    @NotNull(message = "产品ID不能为空！")
    private Long productId;

    @ApiModelProperty(value = "时间数值")
    @NotNull(message = "拜访时间不能为空")
    private Integer time;

    @ApiModelProperty(value = "1是工作日，2是小时")
    @NotNull(message = "请选择工作日或者小时！")
    private Integer unit;

    @ApiModelProperty(value = "类型，目前有的：1是拜访频次，2是转移后拜访频次，3是会议开始前拜访频次，4是会议结束后拜访频次")
    @NotNull(message = "拜访类型不能为空！")
    private Integer visitType;


}
