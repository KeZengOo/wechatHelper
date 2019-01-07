package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 代表拜访频次设置
 * @author tiancun
 * @date 2019-01-07
 */
@Data
@ApiModel(value = "代表拜访频次")
public class ProductVisitFrequencyResponseBean implements Serializable {
    private static final long serialVersionUID = -4682125441392094625L;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "代表对医生的拜访频次")
    private Integer visitFrequency;

    @ApiModelProperty(value = "1是工作日，2是小时")
    private Integer visitFrequencyType;

    @ApiModelProperty(value = "医生转移后，代表拜访频次")
    private  Integer retVisitFrequency;

    @ApiModelProperty(value = "1是工作日，2是小时")
    private Integer retVisitFrequencyType;

    @ApiModelProperty(value = "会议开始前频次拜访")
    private Integer beforeMeetingFrequency;

    @ApiModelProperty(value = "1是工作日，2是小时")
    private Integer beforeMeetingFrequencyType;

    @ApiModelProperty(value = "会议结束后拜访频次")
    private Integer afterMeetingFrequency;

    @ApiModelProperty(value = "1是工作日，2是小时")
    private Integer afterMeetingFrequencyType;

}
