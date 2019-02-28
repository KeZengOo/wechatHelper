package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Map;

@ApiModel("微信拜访记录或消息存在的时间返回类")
@Data
public class VirtualWechatDateReturn {
    @ApiModelProperty(value = "时间列表")
    private Map<String, Integer> events;
    @ApiModelProperty(value = "天数")
    private Integer dayCount;
}
