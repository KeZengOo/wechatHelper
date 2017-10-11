package com.nuoxin.virtual.rep.api.web.controller.request.meeting;

import com.nuoxin.virtual.rep.api.common.bean.PageRequestBean;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Create by tiancun on 2017/10/11
 */

@ApiModel(value = "会议请求类")
public class MeetingRequestBean extends PageRequestBean{
    private static final long serialVersionUID = 4391736961565565344L;

    @ApiModelProperty(value = "会议id")
    private Long id;

    @ApiModelProperty(value = "标题")
    private String title;

    @ApiModelProperty(value = "讲者")
    private String speaker;

    @ApiModelProperty(value = "会议开始时间")
    private String meetingStartTime;

    @ApiModelProperty(value = "会议结束时间")
    private String meetingEndTime;

    @ApiModelProperty(value = "会议总时长")
    private Integer meetingSumTime;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医生的手机号")
    private String telephone;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public String getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(String meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public String getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(String meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Integer getMeetingSumTime() {
        return meetingSumTime;
    }

    public void setMeetingSumTime(Integer meetingSumTime) {
        this.meetingSumTime = meetingSumTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
    }
}
