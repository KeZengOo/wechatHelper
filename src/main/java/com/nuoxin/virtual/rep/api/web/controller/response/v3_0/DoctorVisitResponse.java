package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 医生拜访返回的字段
 * @author tiancun
 * @date 2019-04-28
 */
@ApiModel(value = "医生拜访返回的字段")
@Data
public class DoctorVisitResponse implements Serializable {
    private static final long serialVersionUID = 1445844483595089098L;

    @JsonIgnore
    private Long maxVisitId;

    @ApiModelProperty(value = "上一次拜访时间")
    private Date lastVisitTime;

    @ApiModelProperty(value = "拜访的代表ID")
    private Long visitDrugUserId;

    @ApiModelProperty(value = "拜访的代表姓名")
    private String visitDrugUserName;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult;



}
