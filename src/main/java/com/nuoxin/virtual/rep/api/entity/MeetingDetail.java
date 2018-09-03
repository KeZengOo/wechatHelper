package com.nuoxin.virtual.rep.api.entity;

import com.nuoxin.virtual.rep.api.common.entity.IdEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

/**
 * Create by tiancun on 2017/10/11
 */
@Entity
@Table(name = "t_meeting_attend_details")
public class MeetingDetail extends IdEntity{

    @Column(name = "meeting_id", length = 20)
    private Long meetingId;

    @Column(name = "attend_start_time")
    private String attendStartTime;

    @Column(name = "attend_end_time")
    private String attendEndTime;

    @Column(name = "attend_sum_time")
    private Integer attendSumTime;

    @Column(name = "doctor_id", length = 20)
    private Long doctorId;

    @Column(name = "doctor_name", length = 30)
    private String doctorName;

    @Column(name = "attend_type")
    private Integer attendWay;

    @Column(name = "type")
    private Integer attendType;

    @Column(name = "download")
    private Integer download;

    @Column(name = "doctor_tel", length = 20)
    private String telephone;

    @Column(name = "create_time")
    private Date createTime;


    public Long getMeetingId() {
        return meetingId;
    }

    public void setMeetingId(Long meetingId) {
        this.meetingId = meetingId;
    }

    public String getAttendStartTime() {
        return attendStartTime;
    }

    public void setAttendStartTime(String attendStartTime) {
        this.attendStartTime = attendStartTime;
    }

    public String getAttendEndTime() {
        return attendEndTime;
    }

    public void setAttendEndTime(String attendEndTime) {
        this.attendEndTime = attendEndTime;
    }

    public Integer getAttendSumTime() {
        return attendSumTime;
    }

    public void setAttendSumTime(Integer attendSumTime) {
        this.attendSumTime = attendSumTime;
    }

    public Long getDoctorId() {
        return doctorId;
    }

    public void setDoctorId(Long doctorId) {
        this.doctorId = doctorId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getAttendWay() {
        return attendWay;
    }

    public void setAttendWay(Integer attendWay) {
        this.attendWay = attendWay;
    }

    public Integer getAttendType() {
        return attendType;
    }

    public void setAttendType(Integer attendType) {
        this.attendType = attendType;
    }

    public Integer getDownload() {
        return download;
    }

    public void setDownload(Integer download) {
        this.download = download;
    }
}
