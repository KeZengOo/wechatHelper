package com.nuoxin.virtual.rep.api.entity.v3_0.excel;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 会议导入
 * @author wujiang
 * @date 20190506
 */
public class MeetingSubjectExcel {
    @Excel(name = "会议名称", width = 50)
    private String meetingName;
    @Excel(name = "主题名称", width = 50)
    private String subjectName;
    @Excel(name = "演讲人", width = 50)
    private String speaker;
    @Excel(name = "主题开始时间", width = 100)
    private Date startTime;
    @Excel(name = "主题结束时间", width = 100)
    private Date endTime;

    public String getMeetingName() {
        return meetingName;
    }

    public void setMeetingName(String meetingName) {
        this.meetingName = meetingName;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }

    public String getSpeaker() {
        return speaker;
    }

    public void setSpeaker(String speaker) {
        this.speaker = speaker;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
