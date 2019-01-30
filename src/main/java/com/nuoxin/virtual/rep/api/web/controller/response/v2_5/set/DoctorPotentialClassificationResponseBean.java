package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生输入的潜力和分型的频次
 * @author tiancun
 * @date 2019-01-30
 */
@Data
public class DoctorPotentialClassificationResponseBean implements Serializable {
    private static final long serialVersionUID = -8314433800940286228L;


    @ApiModelProperty(value = "医生的潜力")
    private String potential;

    @ApiModelProperty(value = "医生的分型")
    private String classification;

    @ApiModelProperty(value = "拜访频次")
    private Integer frequency;


}
