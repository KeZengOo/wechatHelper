package com.nuoxin.virtual.rep.api.web.controller.response.hcp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.List;

/**
 * Created by tiancun on 17/8/2.
 */
@ApiModel(value = "主数据医生基本信息")
public class HcpBaseInfoResponseBean implements Serializable{

    private static final long serialVersionUID = -3016151294895528591L;
    @ApiModelProperty(value = "医生姓名")
    private String doctorName;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    //第一个圈
    @ApiModelProperty(value = "医生科室")
    private String depart;

    @ApiModelProperty(value = "医生级别")
    private String doctorLevel;

    @ApiModelProperty(value = "医院级别")
    private String hospitalLevel;

    @ApiModelProperty(value = "所在城市")
    private String city;

    //第二个圈
    @ApiModelProperty(value = "综合评分")
    private String comprehensiveScore = "0.0";

    @ApiModelProperty(value = "患者满意度")
    private String satisfaction = "0%";

    @ApiModelProperty(value = "预约量")
    private Integer orderNum = 0;

    @ApiModelProperty(value = "门诊量")
    private Integer outpatientVolume = 0;


    //第三个圈
    @ApiModelProperty(value = "论文关键词")
    private List<DocKeywordResponseBean> keywordList;




    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getDepart() {
        return depart;
    }

    public void setDepart(String depart) {
        this.depart = depart;
    }

    public String getDoctorLevel() {
        return doctorLevel;
    }

    public void setDoctorLevel(String doctorLevel) {
        this.doctorLevel = doctorLevel;
    }

    public String getHospitalLevel() {
        return hospitalLevel;
    }

    public void setHospitalLevel(String hospitalLevel) {
        this.hospitalLevel = hospitalLevel;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

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

    public List<DocKeywordResponseBean> getKeywordList() {
        return keywordList;
    }

    public void setKeywordList(List<DocKeywordResponseBean> keywordList) {
        this.keywordList = keywordList;
    }
}
