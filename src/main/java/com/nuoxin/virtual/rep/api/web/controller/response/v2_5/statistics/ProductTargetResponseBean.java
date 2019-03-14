package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.statistics;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 工作台日报产品目标
 * @author tiancun
 * @date 2019-02-25
 */
@Data
@ApiModel(value = "工作台日报产品目标")
public class ProductTargetResponseBean implements Serializable {
    private static final long serialVersionUID = -5843233078325756303L;

    @ApiModelProperty(value = "目标医院数量")
    private Integer targetHospital;

    @ApiModelProperty(value = "目标医生数量")
    private Integer targetDoctor;





}