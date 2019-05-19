package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 日报导出明细
 * @author tiancun
 * @date 2019-05-19
 */
@ApiModel(value = "日报导出明细")
@Data
public class ExportDetailResponse implements Serializable {
    private static final long serialVersionUID = 8496248340218508144L;

    @ApiModelProperty(value = "拜访日期")
    private String visitDate;

    @ApiModelProperty(value = "拜访医院数")
    private Integer visitHospital;

    @ApiModelProperty(value = "接触医院数")
    private Integer contactHospital;

    @ApiModelProperty(value = "成功医院数")
    private Integer successHospital;

    @ApiModelProperty(value = "覆盖医院数")
    private Integer coverHospital;

    @ApiModelProperty(value = "接触医生数")
    private Integer contactDoctor;

    @ApiModelProperty(value = "成功医生数")
    private Integer successDoctor;

    @ApiModelProperty(value = "覆盖医生数")
    private Integer coverDoctor;

    @ApiModelProperty(value = "拜访医生数")
    private Integer visitDoctorNum;

    @ApiModelProperty(value = "电话接通医生数")
    private Integer callConnectDoctorNum;

    @ApiModelProperty(value = "微信回复医生数")
    private Integer wechatReplyDoctorNum;

    @ApiModelProperty(value = "面谈医生数")
    private Integer interviewDoctorNum;

    @ApiModelProperty(value = "参会医生数")
    private Integer attendMeetingDoctorNum;

    @ApiModelProperty(value = "阅读医生数")
    private Integer readDoctorNum;

}
