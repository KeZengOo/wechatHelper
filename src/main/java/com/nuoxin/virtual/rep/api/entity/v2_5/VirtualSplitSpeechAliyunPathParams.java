package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("语音分割后的阿里云地址表")
@Data
public class VirtualSplitSpeechAliyunPathParams {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "分割后的音频地址")
    private String targeUrl;
    @ApiModelProperty(value = "原音频地址")
    private String sourceUrl;
    @ApiModelProperty(value = "分隔地址类型：1为左声道，2为右声道")
    private Integer type;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
