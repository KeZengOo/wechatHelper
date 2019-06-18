package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报医院级别招募情况
 * @author tiancun
 * @date 2019-06-18
 */
@Data
@ApiModel(value = "月报医院级别招募情况")
public class MonthlyHospitalLevelRecruitResponse implements Serializable {
    private static final long serialVersionUID = -1367300598769531826L;

    @ApiModelProperty(value = "医院级别")
    private String hospitalLevel;

    @ApiModelProperty(value = "招募医生")
    private Integer recruitDoctor;

    @ApiModelProperty(value = "招募医生占比")
    private String recruitDoctorRate;

}
