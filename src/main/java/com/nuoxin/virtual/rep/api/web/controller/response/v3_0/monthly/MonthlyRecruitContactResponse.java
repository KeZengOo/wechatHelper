package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.monthly;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 月报招募联系方式统计
 * @author tiancun
 * @date 2019-06-14
 */
@Data
@ApiModel(value = "月报招募联系方式统计")
public class MonthlyRecruitContactResponse implements Serializable {
    private static final long serialVersionUID = 6449728544345881633L;

    @ApiModelProperty(value = "招募医生")
    private Integer recruitDoctor;

    @ApiModelProperty(value = "有手机号医生")
    private Integer hasMobileDoctor;

    @ApiModelProperty(value = "有手机号医生和招募医生比率")
    private String hasMobileDoctorRate;

    @ApiModelProperty(value = "有微信号医生")
    private Integer hasWechatDoctor;

    @ApiModelProperty(value = "有微信医生比和招募医生率")
    private String hasWechatDoctorRate;


    @ApiModelProperty(value = "添加微信医生")
    private Integer addWechatDoctor;

    @ApiModelProperty(value = "添加微信医生和招募医生比率")
    private String addWechatDoctorRate;


}
