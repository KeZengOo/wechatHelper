package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2019-06-17
 */
@ApiModel(value = "每月招募成功的医生")
@Data
public class MonthlyRecruitDetailResponse implements Serializable {
    private static final long serialVersionUID = 7974135523186061737L;

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "当月招募成功医生")
    private Integer recruitDoctor;



}
