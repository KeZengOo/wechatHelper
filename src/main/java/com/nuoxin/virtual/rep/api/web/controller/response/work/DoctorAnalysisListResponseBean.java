package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/18
 */
@ApiModel(value = "客户分析返回数据")
public class DoctorAnalysisListResponseBean implements Serializable{
    private static final long serialVersionUID = -7732020247039464525L;


    @ApiModelProperty(value = "总通话时间最短")
    private List<DoctorAnalysisResponseBean> minTotalCallTimeList;

    @ApiModelProperty(value = "平均通话时间最短")
    private List<DoctorAnalysisResponseBean> minAvgCallTimeList;

//    @ApiModelProperty(value = "电话数量最少")
//    private List<DoctorAnalysisResponseBean> minTotalCallCountList;

    @ApiModelProperty(value = "电话覆盖数量最少")
    private List<DoctorAnalysisResponseBean> minCallCoveredCountList;


//    @ApiModelProperty(value = "总短信数量最少")
//    private List<DoctorAnalysisResponseBean> minTotalImCountList;
//
//    @ApiModelProperty(value = "短信覆盖客户数量最少")
//    private List<DoctorAnalysisResponseBean> minImCoveredCountList;
//
//    @ApiModelProperty(value = "微信数量最少")
//    private List<DoctorAnalysisResponseBean> minTotalWechatCountList;
//
//    @ApiModelProperty(value = "微信覆盖客户数最少")
//    private List<DoctorAnalysisResponseBean> minWechatCoveredCountList;
//
//    @ApiModelProperty(value = "邮件最小数量")
//    private List<DoctorAnalysisResponseBean> minEmailCountList;

    @ApiModelProperty(value = "最短微信阅读时长")
    private List<DoctorAnalysisResponseBean> minWechatTimeList;

    @ApiModelProperty(value = "最短短信阅读时长")
    private List<DoctorAnalysisResponseBean> minImTimeList;

    @ApiModelProperty(value = "最短的邮件阅读时长")
    private List<DoctorAnalysisResponseBean> minEmailTimeList;

    @ApiModelProperty(value = "最短的会议时长")
    private List<DoctorAnalysisResponseBean> minMeetingTimeList;


    public List<DoctorAnalysisResponseBean> getMinTotalCallTimeList() {
        return minTotalCallTimeList;
    }

    public void setMinTotalCallTimeList(List<DoctorAnalysisResponseBean> minTotalCallTimeList) {
        this.minTotalCallTimeList = minTotalCallTimeList;
    }

    public List<DoctorAnalysisResponseBean> getMinAvgCallTimeList() {
        return minAvgCallTimeList;
    }

    public void setMinAvgCallTimeList(List<DoctorAnalysisResponseBean> minAvgCallTimeList) {
        this.minAvgCallTimeList = minAvgCallTimeList;
    }


    public List<DoctorAnalysisResponseBean> getMinCallCoveredCountList() {
        return minCallCoveredCountList;
    }

    public void setMinCallCoveredCountList(List<DoctorAnalysisResponseBean> minCallCoveredCountList) {
        this.minCallCoveredCountList = minCallCoveredCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinWechatTimeList() {
        return minWechatTimeList;
    }

    public void setMinWechatTimeList(List<DoctorAnalysisResponseBean> minWechatTimeList) {
        this.minWechatTimeList = minWechatTimeList;
    }

    public List<DoctorAnalysisResponseBean> getMinImTimeList() {
        return minImTimeList;
    }

    public void setMinImTimeList(List<DoctorAnalysisResponseBean> minImTimeList) {
        this.minImTimeList = minImTimeList;
    }

    public List<DoctorAnalysisResponseBean> getMinEmailTimeList() {
        return minEmailTimeList;
    }

    public void setMinEmailTimeList(List<DoctorAnalysisResponseBean> minEmailTimeList) {
        this.minEmailTimeList = minEmailTimeList;
    }

    public List<DoctorAnalysisResponseBean> getMinMeetingTimeList() {
        return minMeetingTimeList;
    }

    public void setMinMeetingTimeList(List<DoctorAnalysisResponseBean> minMeetingTimeList) {
        this.minMeetingTimeList = minMeetingTimeList;
    }
}
