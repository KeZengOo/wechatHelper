package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("微信拜访登记限制条件配置表")
@Data
public class VirtualWechatVisitCountAndCycleConfigParams {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "一个自然天内，只能添加微信拜访记录的条数")
    private Integer addCount;
    @ApiModelProperty(value = "当前代表在过去N天内是否与医生有微信聊天记录")
    private Integer cycleCount;
}
