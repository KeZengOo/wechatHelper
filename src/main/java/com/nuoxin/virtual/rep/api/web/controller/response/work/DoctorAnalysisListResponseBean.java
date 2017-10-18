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

    @ApiModelProperty(value = "电话数量最少")
    private List<DoctorAnalysisResponseBean> minTotalCallCountList;



    @ApiModelProperty(value = "总短信数量最少")
    private List<DoctorAnalysisResponseBean> minTotalImCountList;

    @ApiModelProperty(value = "短信覆盖客户数量最少")
    private List<DoctorAnalysisResponseBean> minImCoveredCountList;

    @ApiModelProperty(value = "微信数量最少")
    private List<DoctorAnalysisResponseBean> minTotalWechatCountList;

    @ApiModelProperty(value = "微信覆盖客户数最少")
    private List<DoctorAnalysisResponseBean> minWechatCoveredCountList;

    @ApiModelProperty(value = "邮件最小数量")
    private List<DoctorAnalysisResponseBean> minEmailCountList;

    @ApiModelProperty(value = "最少的会议时间")
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

    public List<DoctorAnalysisResponseBean> getMinTotalCallCountList() {
        return minTotalCallCountList;
    }

    public void setMinTotalCallCountList(List<DoctorAnalysisResponseBean> minTotalCallCountList) {
        this.minTotalCallCountList = minTotalCallCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinTotalImCountList() {
        return minTotalImCountList;
    }

    public void setMinTotalImCountList(List<DoctorAnalysisResponseBean> minTotalImCountList) {
        this.minTotalImCountList = minTotalImCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinImCoveredCountList() {
        return minImCoveredCountList;
    }

    public void setMinImCoveredCountList(List<DoctorAnalysisResponseBean> minImCoveredCountList) {
        this.minImCoveredCountList = minImCoveredCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinTotalWechatCountList() {
        return minTotalWechatCountList;
    }

    public void setMinTotalWechatCountList(List<DoctorAnalysisResponseBean> minTotalWechatCountList) {
        this.minTotalWechatCountList = minTotalWechatCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinWechatCoveredCountList() {
        return minWechatCoveredCountList;
    }

    public void setMinWechatCoveredCountList(List<DoctorAnalysisResponseBean> minWechatCoveredCountList) {
        this.minWechatCoveredCountList = minWechatCoveredCountList;
    }


    public List<DoctorAnalysisResponseBean> getMinEmailCountList() {
        return minEmailCountList;
    }

    public void setMinEmailCountList(List<DoctorAnalysisResponseBean> minEmailCountList) {
        this.minEmailCountList = minEmailCountList;
    }

    public List<DoctorAnalysisResponseBean> getMinMeetingTimeList() {
        return minMeetingTimeList;
    }

    public void setMinMeetingTimeList(List<DoctorAnalysisResponseBean> minMeetingTimeList) {
        this.minMeetingTimeList = minMeetingTimeList;
    }
}
