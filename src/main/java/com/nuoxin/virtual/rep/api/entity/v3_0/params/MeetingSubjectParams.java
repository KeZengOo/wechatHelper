package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议主题查询
 * @author wujiang
 * @date 20190430
 */
@Data
@ApiModel("会议主题查询")
public class MeetingSubjectParams {
    @ApiModelProperty(value = "id")
    private Integer id;
    @ApiModelProperty(value = "产品ID")
    private Integer productId;
    @ApiModelProperty(value = "会议名称")
    private String meetingName;
    @ApiModelProperty(value = "主题名称")
    private String subjectName;
    @ApiModelProperty(value = "演讲人")
    private String speaker;
    @ApiModelProperty(value = "主题开始时间")
    private String startTime;
    @ApiModelProperty(value = "主题结束时间")
    private String endTime;
    @ApiModelProperty(value = "时长")
    private String duration;
}
