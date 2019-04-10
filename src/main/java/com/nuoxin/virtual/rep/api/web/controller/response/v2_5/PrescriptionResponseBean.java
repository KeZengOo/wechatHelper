package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 处方信息返回字段
 * @author tiancun
 * @date 2018-10-31
 */
@ApiModel(value = "处方信息返回字段")
@Data
public class PrescriptionResponseBean implements Serializable {
    private static final long serialVersionUID = 5767741042064002760L;

    @ApiModelProperty(value = "医生id")
    private Long doctorId;

    /**
     * 所有的都给了默认值
     */
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

    @ApiModelProperty(value = "是否有AE")
    private Integer isHasAe;

}
