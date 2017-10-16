package com.nuoxin.virtual.rep.api.web.controller.request.vo;

import com.nuoxin.virtual.rep.api.common.annotations.Excel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 会议详情导入
 * Create by tiancun on 2017/10/11
 */
public class MeetingDetailVo {

    @Excel(name = "医生手机号")
    private String telephone;

    @Excel(name = "开始时间")
    private Date attendStartTime;

    @Excel(name = "结束时间")
    private Date attendEndTime;

    @ApiModelProperty(value = "参会方式（1-网站，2-电话，3微信）")
    private Integer attendWay;

    @ApiModelProperty(value = "类型（参会-1，or回看-2）")
    private Integer attendType;

    @ApiModelProperty(value = "是否下载（1-下载，2未下载）")
    private Integer download;


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
