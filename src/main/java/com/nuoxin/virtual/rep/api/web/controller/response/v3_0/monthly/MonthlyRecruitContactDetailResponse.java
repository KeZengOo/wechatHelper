package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报招募联系方式每月详情统计
 * @author tiancun
 * @date 2019-06-14
 */
@Data
@ApiModel(value = "月报招募联系方式每月详情统计")
public class MonthlyRecruitContactDetailResponse implements Serializable{

    private static final long serialVersionUID = 2945478986082093372L;

    @ApiModelProperty(value = "月份")
    private String month;

    @ApiModelProperty(value = "当月成功招募医生数")
    private Integer monthRecruitDoctor;

    @ApiModelProperty(value = "当月有手机号医生")
    private Integer hasMobileDoctor;

    @ApiModelProperty(value = "当月有手机号医生比率")
    private String hasMobileDoctorRate;

    @ApiModelProperty(value = "当月有微信医生")
    private Integer hasWechatDoctor;

    @ApiModelProperty(value = "当月有微信医生比率")
    private String hasWechatDoctorRate;

    @ApiModelProperty(value = "当月已添加微信医生")
    private Integer addWechatDoctor;

    @ApiModelProperty(value = "当月已添加微信的比率")
    private String addWechatDoctorRate;

}
