package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生输入的潜力
 * @author tiancun
 * @date 2019-01-30
 */
@Data
public class DoctorPotentialResponseBean implements Serializable {
    private static final long serialVersionUID = -4255519652619619551L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生输入的潜力")
    private String potential;

}
