package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报科室招募情况
 * @author tiancun
 * @date 2019-06-18
 */
@ApiModel(value = "月报科室招募情况")
@Data
public class MonthlyDepartRecruitResponse implements Serializable {
    private static final long serialVersionUID = 7278360743966058978L;

    @ApiModelProperty(value = "科室")
    private String depart;

    @ApiModelProperty(value = "科室招募医生数")
    private Integer recruitDoctor;

    @ApiModelProperty(value = "科室招募医生占比")
    private String recruitDoctorRate;


}
