package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel("微信拜访记录或消息存在的时间")
@Data
public class VirtualWechatDate {
    @ApiModelProperty(value = "存在日期")
    private String existDate;
}
