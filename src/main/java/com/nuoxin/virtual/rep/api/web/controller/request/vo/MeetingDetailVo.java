package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;

import java.util.Date;

/**
 * 会议详情导入
 * Create by tiancun on 2017/10/11
 */
public class MeetingDetailVo {

    @Excel(name = "医生参会开始时间")
    private Date attendStartTime;

    @Excel(name = "医生参会结束时间")
    private Date attendEndTime;

    @Excel(name = "参会医生手机号")
    private String telephone;


    public Date getAttendStartTime() {
        return attendStartTime;
    }

    public void setAttendStartTime(Date attendStartTime) {
        this.attendStartTime = attendStartTime;
    }

    public Date getAttendEndTime() {
        return attendEndTime;
    }

    public void setAttendEndTime(Date attendEndTime) {
        this.attendEndTime = attendEndTime;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
