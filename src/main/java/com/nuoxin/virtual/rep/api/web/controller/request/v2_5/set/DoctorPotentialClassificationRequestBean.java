package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.set;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 医生输入的潜力和分型的汇总
 * @author tiancun
 * @date 2019-01-30
 */
@Data
public class DoctorPotentialClassificationRequestBean implements Serializable {
    private static final long serialVersionUID = -8314433800940286228L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生的潜力")
    private String potential;

    @ApiModelProperty(value = "医生的分型")
    private String classification;


    @ApiModelProperty(value = "拜访频次")
    private Integer frequency;




}
