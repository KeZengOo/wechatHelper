package com.nuoxin.virtual.rep.api.entity.v3_0.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议主题查询Request
 * @author wujiang
 * @date 20190430
 */
@Data
@ApiModel("会议主题查询Request")
public class MeetingSubjectRequest {
    @ApiModelProperty(value = "产品ID")
    private Integer productId;
    @ApiModelProperty(value = "会议Id")
    private Long meetingId;
    @ApiModelProperty(value = "会议名称")
    private String meetingName;
}
