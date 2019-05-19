package com.nuoxin.virtual.rep.api.web.controller.response.v3_0.daily;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生拜访的指标统计
 * @author tiancun
 * @date 2019-05-19
 */
@Data
@ApiModel(value = "医生拜访的指标统计")
public class DoctorVisitResponse implements Serializable {
    private static final long serialVersionUID = -1083339845361627416L;

    @ApiModelProperty(value = "医生回复人数")
    private Integer doctorReplyDoctorNum;

    @ApiModelProperty(value = "医生回复次数")
    private Integer doctorReplyDoctorCount;

    @ApiModelProperty(value = "有微信医生人数")
    private Integer hasWechatDoctorNum;

    @ApiModelProperty(value = "添加微信医生人数")
    private Integer addWechatDoctorNum;

    @ApiModelProperty(value = "有AE医生人数")
    private Integer hasAeDoctorNum;

    @ApiModelProperty(value = "有需求医生人数")
    private Integer hasDemandDoctorNum;

    @ApiModelProperty(value = "退出项目医生人数")
    private Integer quitDoctorNum;

}
