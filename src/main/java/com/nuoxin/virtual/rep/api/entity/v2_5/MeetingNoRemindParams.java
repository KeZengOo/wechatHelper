package com.nuoxin.virtual.rep.api.entity.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 会议不需要提醒的医生
 * @author tiancun
 * @date 2019-01-11
 */
@Data
public class MeetingNoRemindParams implements Serializable {

    private static final long serialVersionUID = 6913464239795791624L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "会议ID")
    private Long meetingId;




}
