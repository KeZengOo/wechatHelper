package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.TreeMap;

/**
 * Created by tiancun on 17/8/2.
 */
@ApiModel(value = "医生社会影响力返回的数据")
public class HcpSocietyResponseBean implements Serializable{

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "综合评分")
    private String comprehensiveScore = "0.0";

    @ApiModelProperty(value = "患者满意度")
    private String satisfaction = "0%";

    @ApiModelProperty(value = "预约量")
    private Integer orderNum = 0;

    @ApiModelProperty(value = "门诊量")
    private Integer outpatientVolume = 0;

    @ApiModelProperty(value = "综合评分趋势图")
    private TreeMap<Integer, String> comprehensiveScoreMap;

    @ApiModelProperty(value = "满意度趋势图")
    private TreeMap<Integer, String> satisfactionMap;

    @ApiModelProperty(value = "预约量趋势图")
    private TreeMap<Integer, Integer> orderNumMap;

    @ApiModelProperty(value = "门诊量趋势图")
    private TreeMap<Integer, Integer> outpatientVolumeMap;


    public String getComprehensiveScore() {
        return comprehensiveScore;
    }

    public void setComprehensiveScore(String comprehensiveScore) {
        this.comprehensiveScore = comprehensiveScore;
    }

    public String getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(String satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getOutpatientVolume() {
        return outpatientVolume;
    }

    public void setOutpatientVolume(Integer outpatientVolume) {
        this.outpatientVolume = outpatientVolume;
    }

    public TreeMap<Integer, String> getComprehensiveScoreMap() {
        return comprehensiveScoreMap;
    }

    public void setComprehensiveScoreMap(TreeMap<Integer, String> comprehensiveScoreMap) {
        this.comprehensiveScoreMap = comprehensiveScoreMap;
    }

    public TreeMap<Integer, String> getSatisfactionMap() {
        return satisfactionMap;
    }

    public void setSatisfactionMap(TreeMap<Integer, String> satisfactionMap) {
        this.satisfactionMap = satisfactionMap;
    }

    public TreeMap<Integer, Integer> getOrderNumMap() {
        return orderNumMap;
    }

    public void setOrderNumMap(TreeMap<Integer, Integer> orderNumMap) {
        this.orderNumMap = orderNumMap;
    }

    public TreeMap<Integer, Integer> getOutpatientVolumeMap() {
        return outpatientVolumeMap;
    }

    public void setOutpatientVolumeMap(TreeMap<Integer, Integer> outpatientVolumeMap) {
        this.outpatientVolumeMap = outpatientVolumeMap;
    }
}
