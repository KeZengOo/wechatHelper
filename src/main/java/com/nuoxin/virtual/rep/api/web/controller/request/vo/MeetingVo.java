package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

import java.util.Date;

/**
 * 会议导入
 * Create by tiancun on 2017/10/11
 */
public class MeetingVo {


    @Excel(name = "会议标题", width = 50)
    private String title;

    @Excel(name = "讲者", width = 30)
    private String speaker;

    @Excel(name = "会议开始时间", width = 30)
    private Date meetingStartTime;

    @Excel(name = "会议结束时间", width = 30)
    private Date meetingEndTime;


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

    public Date getMeetingStartTime() {
        return meetingStartTime;
    }

    public void setMeetingStartTime(Date meetingStartTime) {
        this.meetingStartTime = meetingStartTime;
    }

    public Date getMeetingEndTime() {
        return meetingEndTime;
    }

    public void setMeetingEndTime(Date meetingEndTime) {
        this.meetingEndTime = meetingEndTime;
    }



}
