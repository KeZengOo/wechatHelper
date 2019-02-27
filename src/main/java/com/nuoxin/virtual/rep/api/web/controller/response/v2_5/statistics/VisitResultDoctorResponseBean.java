package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生拜访结果的次数
 * @author tiancun
 * @date 2019-02-26
 */
@Data
@ApiModel(value = "医生拜访结果次数")
public class VisitResultDoctorResponseBean implements Serializable {
    private static final long serialVersionUID = 8944484090754595930L;

    @ApiModelProperty(value = "医生次数")
    private Integer doctorCount;

    @ApiModelProperty(value = "拜访结果")
    private String visitResult;



}
