package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生所属产品
 * @author tiancun
 * @date 2019-05-08
 */
@Data
@ApiModel(value = "医生所属产品")
public class DoctorProductResponse implements Serializable {
    private static final long serialVersionUID = -5348838026330291939L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "产品ID")
    private Long productId;

    @ApiModelProperty(value = "产品名称")
    private String productName;


}
