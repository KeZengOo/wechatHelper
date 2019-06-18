package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报医生级别招募情况
 * @author tiancun
 * @date 2019-06-18
 */
@Data
@ApiModel(value = "月报医生级别招募情况")
public class MonthlyDoctorLevelRecruitResponse implements Serializable {
    private static final long serialVersionUID = -4377734788589994452L;

    @ApiModelProperty(value = "医生级别")
    private String doctorLevel;

    @ApiModelProperty(value = "招募医生数")
    private Integer recruitDoctor;

    @ApiModelProperty(value = "招募医生数比率")
    private String recruitDoctorRate;


}
