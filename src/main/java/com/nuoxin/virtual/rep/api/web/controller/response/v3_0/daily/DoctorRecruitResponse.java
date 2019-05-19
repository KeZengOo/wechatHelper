package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生招募统计
 * @author tiancun
 * @date 2019-05-19
 */
@Data
@ApiModel(value = "医生招募统计")
public class DoctorRecruitResponse implements Serializable {
    private static final long serialVersionUID = -4793525246244518327L;

    @ApiModelProperty(value = "目标医生")
    private Integer targetDoctor;

    @ApiModelProperty(value = "已招募医生")
    private Integer recruitDoctorNum;

    @ApiModelProperty(value = "未招募医生")
    private Integer noRecruitDoctorNum;

    @ApiModelProperty(value = "招募率")
    private String recruitRate;


}
