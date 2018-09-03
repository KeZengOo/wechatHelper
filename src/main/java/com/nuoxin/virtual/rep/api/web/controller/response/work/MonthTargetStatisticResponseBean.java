package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * Create by tiancun on 2017/10/12
 */
@ApiModel(value = "本月目标统计")
public class MonthTargetStatisticResponseBean implements Serializable{
    private static final long serialVersionUID = -1414441310341802358L;

    @ApiModelProperty(value = "本月目标拜访客户数")
    private Integer targetVisitSum;

    @ApiModelProperty(value = "本月目标拜访克数次数")
    private Integer targetVisitCount;


    @ApiModelProperty(value = "本月目标呼出人数")
    private Integer targetCallSum;

    @ApiModelProperty(value = "本月目标呼出人次")
    private Integer targetCallCount;

    @ApiModelProperty(value = "本月目标呼出时间，单位分钟")
    private Integer targetCallTime;

    @ApiModelProperty(value = "本月目标多渠道会话人数")
    private Integer targetMessageSum;

    @ApiModelProperty(value = "本月目标多渠道会话人次")
    private Integer targetMessageCount;


    @ApiModelProperty(value = "本月目标会议人数")
    private Integer targetMeetingSum;

    @ApiModelProperty(value = "本月目标会议时长,单位分钟")
    private Integer targetMeetingTotalTime;


    public Integer getTargetVisitSum() {
        return targetVisitSum;
    }

    public void setTargetVisitSum(Integer targetVisitSum) {
        this.targetVisitSum = targetVisitSum;
    }

    public Integer getTargetVisitCount() {
        return targetVisitCount;
    }

    public void setTargetVisitCount(Integer targetVisitCount) {
        this.targetVisitCount = targetVisitCount;
    }

    public Integer getTargetCallSum() {
        return targetCallSum;
    }

    public void setTargetCallSum(Integer targetCallSum) {
        this.targetCallSum = targetCallSum;
    }

    public Integer getTargetCallCount() {
        return targetCallCount;
    }

    public void setTargetCallCount(Integer targetCallCount) {
        this.targetCallCount = targetCallCount;
    }

    public Integer getTargetCallTime() {
        return targetCallTime;
    }

    public void setTargetCallTime(Integer targetCallTime) {
        this.targetCallTime = targetCallTime;
    }

    public Integer getTargetMessageSum() {
        return targetMessageSum;
    }

    public void setTargetMessageSum(Integer targetMessageSum) {
        this.targetMessageSum = targetMessageSum;
    }

    public Integer getTargetMessageCount() {
        return targetMessageCount;
    }

    public void setTargetMessageCount(Integer targetMessageCount) {
        this.targetMessageCount = targetMessageCount;
    }

    public Integer getTargetMeetingSum() {
        return targetMeetingSum;
    }

    public void setTargetMeetingSum(Integer targetMeetingSum) {
        this.targetMeetingSum = targetMeetingSum;
    }


    public Integer getTargetMeetingTotalTime() {
        return targetMeetingTotalTime;
    }

    public void setTargetMeetingTotalTime(Integer targetMeetingTotalTime) {
        this.targetMeetingTotalTime = targetMeetingTotalTime;
    }
}
