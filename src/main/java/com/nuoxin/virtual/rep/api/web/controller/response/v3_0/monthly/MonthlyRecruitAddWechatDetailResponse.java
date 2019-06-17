package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 每月招募的医生新增微信医生
 * @author tiancun
 * @date 2019-06-17
 */
@ApiModel(value = "每月招募的医生新增微信医生")
@Data
public class MonthlyRecruitAddWechatDetailResponse implements Serializable {
    private static final long serialVersionUID = -7465709881581347963L;

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "新增微信医生数")
    private Integer addWechatDoctor;

}
