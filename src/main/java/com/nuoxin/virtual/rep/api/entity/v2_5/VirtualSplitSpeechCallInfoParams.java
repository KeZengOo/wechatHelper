package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel("语音分割识别对话表")
@Data
public class VirtualSplitSpeechCallInfoParams {
    @ApiModelProperty(value = "ID")
    private Long id;
    @ApiModelProperty(value = "原音频标识")
    private String sinToken;
    @ApiModelProperty(value = "分割声道类型：1为左声道，2为右声道")
    private Integer type;
    @ApiModelProperty(value = "短句开始时间")
    private Integer beginTime;
    @ApiModelProperty(value = "代表ID")
    private Integer virtualDrugUserId;
    @ApiModelProperty(value = "对话内容")
    private String text;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
}
