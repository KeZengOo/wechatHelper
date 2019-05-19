package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 拜访医生统计
 * @author tiancun
 * @date 2019-05-17
 */
@Data
@ApiModel(value = "拜访医生统计")
public class VisitDoctorStatisticsResponse implements Serializable {
    private static final long serialVersionUID = -7234404839579754772L;

    @ApiModelProperty(value = "拜访日期")
    private String visitDate;

    @ApiModelProperty(value = "医生数量")
    private Integer doctorNum;



}
