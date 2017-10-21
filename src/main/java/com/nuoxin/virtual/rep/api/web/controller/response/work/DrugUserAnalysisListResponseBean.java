package com.nuoxin.virtual.rep.api.web.controller.response.work;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Create by tiancun on 2017/10/17
 */
@ApiModel(value = "坐席分析返回数据")
public class DrugUserAnalysisListResponseBean implements Serializable{
    private static final long serialVersionUID = -645082110497219239L;

    @ApiModelProperty(value = "总通话时间最短")
    private List<DrugUserAnalysisResponseBean> minTotalCallTimeList;

    @ApiModelProperty(value = "平均通话时间最短")
    private List<DrugUserAnalysisResponseBean> minAvgCallTimeList;

    @ApiModelProperty(value = "电话数量最少")
    private List<DrugUserAnalysisResponseBean> minTotalCallCountList;

    @ApiModelProperty(value = "电话覆盖数量最少")
    private List<DrugUserAnalysisResponseBean> minCallCoveredCountList;

    @ApiModelProperty(value = "总短信数量最少")
    private List<DrugUserAnalysisResponseBean> minTotalImCountList;

    @ApiModelProperty(value = "短信覆盖客户数量最少")
    private List<DrugUserAnalysisResponseBean> minImCoveredCountList;

    @ApiModelProperty(value = "微信数量最少")
    private List<DrugUserAnalysisResponseBean> minTotalWechatCountList;

    @ApiModelProperty(value = "微信覆盖客户数最少")
    private List<DrugUserAnalysisResponseBean> minWechatCoveredCountList;

    @ApiModelProperty(value = "邮件数最少")
    private List<DrugUserAnalysisResponseBean> minEmailCountList;

    @ApiModelProperty(value = "邮件覆盖客户数最少")
    private List<DrugUserAnalysisResponseBean> minEmailCoveredCountList;


    @ApiModelProperty(value = "通话未达标")
    private List<String> callNoReachList;

    @ApiModelProperty(value = "微信未达标")
    private List<String> wechatNoReachList;

    @ApiModelProperty(value = "短信未达标")
    private List<String> imNoReachList;

    @ApiModelProperty(value = "邮件未达标")
    private List<String> emailNoReachList;

    @ApiModelProperty(value = "脱落客户最严重")
    private List<DropCustomerListResponseBean> dropCustomerList;

    public List<DrugUserAnalysisResponseBean> getMinTotalCallTimeList() {
        return minTotalCallTimeList;
    }

    public void setMinTotalCallTimeList(List<DrugUserAnalysisResponseBean> minTotalCallTimeList) {
        this.minTotalCallTimeList = minTotalCallTimeList;
    }

    public List<DrugUserAnalysisResponseBean> getMinAvgCallTimeList() {
        return minAvgCallTimeList;
    }

    public void setMinAvgCallTimeList(List<DrugUserAnalysisResponseBean> minAvgCallTimeList) {
        this.minAvgCallTimeList = minAvgCallTimeList;
    }

    public List<DrugUserAnalysisResponseBean> getMinTotalCallCountList() {
        return minTotalCallCountList;
    }

    public void setMinTotalCallCountList(List<DrugUserAnalysisResponseBean> minTotalCallCountList) {
        this.minTotalCallCountList = minTotalCallCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinCallCoveredCountList() {
        return minCallCoveredCountList;
    }

    public void setMinCallCoveredCountList(List<DrugUserAnalysisResponseBean> minCallCoveredCountList) {
        this.minCallCoveredCountList = minCallCoveredCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinTotalImCountList() {
        return minTotalImCountList;
    }

    public void setMinTotalImCountList(List<DrugUserAnalysisResponseBean> minTotalImCountList) {
        this.minTotalImCountList = minTotalImCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinImCoveredCountList() {
        return minImCoveredCountList;
    }

    public void setMinImCoveredCountList(List<DrugUserAnalysisResponseBean> minImCoveredCountList) {
        this.minImCoveredCountList = minImCoveredCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinTotalWechatCountList() {
        return minTotalWechatCountList;
    }

    public void setMinTotalWechatCountList(List<DrugUserAnalysisResponseBean> minTotalWechatCountList) {
        this.minTotalWechatCountList = minTotalWechatCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinWechatCoveredCountList() {
        return minWechatCoveredCountList;
    }

    public void setMinWechatCoveredCountList(List<DrugUserAnalysisResponseBean> minWechatCoveredCountList) {
        this.minWechatCoveredCountList = minWechatCoveredCountList;
    }

    public List<String> getCallNoReachList() {
        return callNoReachList;
    }

    public void setCallNoReachList(List<String> callNoReachList) {
        this.callNoReachList = callNoReachList;
    }

    public List<String> getWechatNoReachList() {
        return wechatNoReachList;
    }

    public void setWechatNoReachList(List<String> wechatNoReachList) {
        this.wechatNoReachList = wechatNoReachList;
    }

    public List<String> getImNoReachList() {
        return imNoReachList;
    }

    public void setImNoReachList(List<String> imNoReachList) {
        this.imNoReachList = imNoReachList;
    }


    public List<DropCustomerListResponseBean> getDropCustomerList() {
        return dropCustomerList;
    }

    public void setDropCustomerList(List<DropCustomerListResponseBean> dropCustomerList) {
        this.dropCustomerList = dropCustomerList;
    }

    public List<DrugUserAnalysisResponseBean> getMinEmailCountList() {
        return minEmailCountList;
    }

    public void setMinEmailCountList(List<DrugUserAnalysisResponseBean> minEmailCountList) {
        this.minEmailCountList = minEmailCountList;
    }

    public List<DrugUserAnalysisResponseBean> getMinEmailCoveredCountList() {
        return minEmailCoveredCountList;
    }

    public void setMinEmailCoveredCountList(List<DrugUserAnalysisResponseBean> minEmailCoveredCountList) {
        this.minEmailCoveredCountList = minEmailCoveredCountList;
    }

    public List<String> getEmailNoReachList() {
        return emailNoReachList;
    }

    public void setEmailNoReachList(List<String> emailNoReachList) {
        this.emailNoReachList = emailNoReachList;
    }
}
