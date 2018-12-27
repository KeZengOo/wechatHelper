package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 处方信息返回字段
 * @author tiancun
 * @date 2018-10-31
 */
@ApiModel(value = "处方信息返回字段")
@Data
public class PrescriptionRequestBean implements Serializable {
    private static final long serialVersionUID = 5767741042064002760L;

    @ApiModelProperty(value = "医生ID")
    @NotNull(message = "医生ID不能为空")
    private Long doctorId;

    @ApiModelProperty(value = "产品ID")
    @NotNull(message = "产品ID不能为空")
    private Long productId;

    @ApiModelProperty(value = "医生潜力:3高,2中,1低,-1未知")
    private Integer potential;

    @ApiModelProperty(value = "是否有药:1是,0否,-1未知")
    private Integer hasDrug;

    @ApiModelProperty(value = "是否是目标客户:1是,0否 -1未知")
    private Integer target;

    @ApiModelProperty(value = "是否招募:1是,0否,-1未知")
    private Integer recruit;

    @ApiModelProperty(value = "是否脱落:1是,0否,-1未知")
    private Integer breakOff;



}
