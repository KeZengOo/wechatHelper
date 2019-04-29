package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生的手机号
 * @author tiancun
 * @date 2019-04-29
 */
@Data
@ApiModel(value = "医生的手机号")
public class DoctorTelephoneResponse implements Serializable {
    private static final long serialVersionUID = -6363582689140170218L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生手机号")
    private String telephone;


}
