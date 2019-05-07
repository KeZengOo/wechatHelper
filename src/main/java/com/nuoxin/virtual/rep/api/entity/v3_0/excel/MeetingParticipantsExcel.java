package com.nuoxin.virtual.rep.api.entity.v3_0.excel;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

/**
 * 参会导入
 * @author wujiang
 * @date 20190507
 */
public class MeetingParticipantsExcel {
    @Excel(name = "会议ID", width = 50)
    private String meetingId;
    @Excel(name = "医生ID", width = 50)
    private String doctorId;
    @Excel(name = "医生名称", width = 50)
    private String doctorName;
    @Excel(name = "手机号", width = 100)
    private String doctorTel;
    @Excel(name = "参会时长", width = 50)
    private String attendSumTime;
    @Excel(name = "进入直播时间", width = 100)
    private String attendStartTime;
    @Excel(name = "类型，用于区分是否回看(1--参会，2--回看)", width = 50)
    private String type;
    @Excel(name = "退出直播时间", width = 100)
    private String attendEndTime;
    @Excel(name = "会议项目ID", width = 50)
    private String itemId;

    public String getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(String meetingId) {
        this.meetingId = meetingId;
    }

    public String getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorTel() {
        return doctorTel;
    }

    public void setDoctorTel(String doctorTel) {
        this.doctorTel = doctorTel;
    }

    public String getAttendSumTime() {
        return attendSumTime;
    }

    public void setAttendSumTime(String attendSumTime) {
        this.attendSumTime = attendSumTime;
    }

    public String getAttendStartTime() {
        return attendStartTime;
    }

    public void setAttendStartTime(String attendStartTime) {
        this.attendStartTime = attendStartTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAttendEndTime() {
        return attendEndTime;
    }

    public void setAttendEndTime(String attendEndTime) {
        this.attendEndTime = attendEndTime;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }
}
