package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议参会信息表
 * @author wujiang
 * @date 2019-03-28
 */
@ApiModel("会议参会信息表")
@Data
public class EnterpriseMeetingAttendDetailsBean {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;
    @ApiModelProperty(value = "医生名称")
    private String doctorName;
    @ApiModelProperty(value = "会议code")
    private String businessCode;
    @ApiModelProperty(value = "会议开始时间")
    private Date attendStartTime;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "科室")
    private String depart;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;
    @ApiModelProperty(value = "更新时间")
    private Date updateTime;
    @ApiModelProperty(value = "从库会议id")
    private String meetingId;

}
