package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 诺和内部会议记录表
 * @author wujiang
 * @date 2019-03-28
 */
@ApiModel("诺和内部会议记录表")
@Data
public class EnterpriseInternalMeetingBean {

    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "演讲主题")
    private String title;
    @ApiModelProperty(value = "演讲开始时间")
    private Date startTime;
    @ApiModelProperty(value = "演讲结束时间")
    private Date endTime;
    @ApiModelProperty(value = "演讲人")
    private String speaker;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "年份")
    private String year;
}
