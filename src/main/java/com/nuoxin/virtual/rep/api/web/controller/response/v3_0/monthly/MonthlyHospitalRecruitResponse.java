package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报医院招募统计
 * @author tiancun
 * @date 2019-06-12
 */
@ApiModel(value = "月报医院招募统计")
@Data
public class MonthlyHospitalRecruitResponse implements Serializable {
    private static final long serialVersionUID = 6499709891414810868L;

    @ApiModelProperty(value = "目标医院")
    private Integer targetHospital;

    @ApiModelProperty(value = "联系医院")
    private Integer contactHospital;

    @ApiModelProperty(value = "接触医院")
    private Integer touchHospital;

    @ApiModelProperty(value = "招募医院")
    private Integer recruitHospital;

    @ApiModelProperty(value = "联系医院转化率")
    private String contactHospitalRate;

    @ApiModelProperty(value = "接触医生转化率")
    private String touchHospitalRate;

    @ApiModelProperty(value = "招募医院转化率")
    private String recruitHospitalRate;

}
