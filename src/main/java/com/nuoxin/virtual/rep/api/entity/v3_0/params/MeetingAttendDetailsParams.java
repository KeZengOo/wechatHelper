package com.nuoxin.virtual.rep.api.entity.v3_0.params;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 参会列表
 * @author wujiang
 * @date 20190502
 */
@ApiModel("参会详情列表")
@Data
public class MeetingAttendDetailsParams {
    @ApiModelProperty(value = "id")
    private Long id;
    @ApiModelProperty("医生ID")
    private Long doctorId;
    @ApiModelProperty("医生姓名")
    private String doctorName;
    @ApiModelProperty("医院ID")
    private Integer hospitalId;
    @ApiModelProperty("医院姓名")
    private String hospitalName;
    @ApiModelProperty("科室")
    private String depart;
    @ApiModelProperty("参会开始时间")
    private Date attendStartTime;
    @ApiModelProperty("参会结束时间")
    private Date attendEndTime;
    @ApiModelProperty("参会时长")
    private Integer attendSumTime;
    @ApiModelProperty("参会总时长")
    private Integer attendSumCountTime;
    @ApiModelProperty("会议ID")
    private Long meetingId;
    @ApiModelProperty("类型")
    private Integer type;
    @ApiModelProperty("该医生所有参会开始时间和结束时间数组")
    private String[] attendTimeArray;
    @ApiModelProperty("该医生所有参会时长数组")
    private String[] attendSumTimeArray;

}
