package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每月成功招募有手机号医生
 * @author tiancun
 * @date 2019-06-17
 */
@ApiModel(value = "每月成功招募有手机号医生")
@Data
public class MonthlyRecruitMobileDetailResponse implements Serializable {
    private static final long serialVersionUID = -5872553994482258837L;

    @ApiModelProperty(value = "当月")
    private String month;

    @ApiModelProperty(value = "当月招募成功医生中有手机号的医生")
    private Integer hasMobileDoctor;


}
