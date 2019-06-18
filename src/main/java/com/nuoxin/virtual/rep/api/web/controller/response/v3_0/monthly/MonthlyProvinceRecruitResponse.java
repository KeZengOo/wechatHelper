package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报各个省招募情况
 * @author tiancun
 * @date 2019-06-18
 */
@ApiModel(value = "月报各个省招募情况")
@Data
public class MonthlyProvinceRecruitResponse implements Serializable {
    private static final long serialVersionUID = 3958613629631726101L;

    @ApiModelProperty(value = "省份")
    private String province;

    @ApiModelProperty(value = "招募医院")
    private Integer recruitHospital;

    @ApiModelProperty(value = "招募医生")
    private Integer recruitDoctor;




}
