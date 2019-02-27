package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * 医生统计
 * @author tiancun
 * @date 2019-02-22
 */
@Data
@ApiModel(value = "日报统计")
public class DailyStatisticsResponseBean implements Serializable {
    private static final long serialVersionUID = -6767891758680932393L;

    @ApiModelProperty(value = "目标医院")
    private Integer targetHospital;

    @ApiModelProperty(value = "目标医生人数")
    private Integer targetDoctor;

    @ApiModelProperty(value = "目标医生招募达成率")
    private String targetRecruitDoctorRate;


    @ApiModelProperty(value = "目标医院招募达成率")
    private String targetRecruitHospitalRate;


    @ApiModelProperty(value = "招募医院数")
    private Integer recruitHospital;


    @ApiModelProperty(value = "招募医生人数")
    private Integer recruitDoctor;


    @ApiModelProperty(value = "新增招募医院数")
    private Integer addRecruitHospital;

    @ApiModelProperty(value = "新增招募医生数量")
    private Integer addRecruitDoctor;

    @ApiModelProperty(value = "拜访医院数")
    private Integer visitHospital;

    @ApiModelProperty(value = "接触医院数")
    private Integer contactHospital;

    @ApiModelProperty(value = "覆盖医院数")
    private Integer coverHospital;

    @ApiModelProperty(value = "成功医院数")
    private Integer successHospital;

    @ApiModelProperty(value = "拜访医生人数")
    private Integer visitDoctor;

    @ApiModelProperty(value = "接触医生人数")
    private Integer contactDoctor;



    @ApiModelProperty(value = "覆盖医生人数")
    private Integer coverDoctor;

    @ApiModelProperty(value = "成功医生数")
    private Integer successDoctor;

    @ApiModelProperty(value = "有需求医生")
    private Integer demandDoctor;

    @ApiModelProperty(value = "有AE的医生数量")
    private Integer hasAeDoctor;

    @ApiModelProperty(value = "电话外呼量")
    private Integer doctorCall;

    @ApiModelProperty(value = "电话接通数量")
    private Integer doctorConnectCall;

    @ApiModelProperty(value = "通话时长，单位分,保留一位")
    private String callTime;

    @ApiModelProperty(value = "面谈拜访次数")
    private Integer interviewVisit;

    @ApiModelProperty(value = "拜访方式为微信/短信/邮件的次数")
    private Integer otherVisit;

    @ApiModelProperty(value = "未招募医院数")
    private Integer noRecruitHospital;

    @ApiModelProperty(value = "未招募医生数")
    private Integer noRecruitDoctor;

    @ApiModelProperty(value = "医生微信回复次数")
    private Integer doctorWechatReplyCount;

    @ApiModelProperty(value = "医生微信回复的数量")
    private Integer doctorWechatReplyNum;

    @ApiModelProperty(value = "有微信医生")
    private Integer hasWechatDoctor;

    @ApiModelProperty(value = "添加微信医生数量")
    private Integer addWechatDoctor;

    @ApiModelProperty(value = "不同拜访结果的医生数量")
    private List<VisitResultDoctorResponseBean> visitResultDoctor = new ArrayList<>();

    @ApiModelProperty(value = "退出项目的医院数量")
    private Integer breakOffHospital;

    @ApiModelProperty(value = "退出项目的医生数量")
    private Integer breakOffDoctor;



}
