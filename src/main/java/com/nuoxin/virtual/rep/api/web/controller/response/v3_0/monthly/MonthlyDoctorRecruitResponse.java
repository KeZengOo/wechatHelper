package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报医生招募统计
 * @author tiancun
 * @date 2019-06-12
 */
@ApiModel(value = "月报医生招募统计")
@Data
public class MonthlyDoctorRecruitResponse implements Serializable {
    private static final long serialVersionUID = 6499709891414810868L;

    @ApiModelProperty(value = "目标医生")
    private Integer targetDoctor;

    @ApiModelProperty(value = "联系医生")
    private Integer contactDoctor;

    @ApiModelProperty(value = "接触医生")
    private Integer touchDoctor;

    @ApiModelProperty(value = "招募医生")
    private Integer recruitDoctor;

    @ApiModelProperty(value = "联系医生转化率")
    private String contactDoctorRate;

    @ApiModelProperty(value = "接触医生转化率")
    private String touchDoctorRate;

    @ApiModelProperty(value = "招募医生转化率")
    private String recruitDoctorRate;

}
