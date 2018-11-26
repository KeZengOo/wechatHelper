package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生联系方式及通话次数
 * @author tiancun
 * @date 2018-11-09
 */
@Data
@ApiModel(value = "医生联系方式及通话次数")
public class CallTelephoneReponseBean implements Serializable {
    private static final long serialVersionUID = -6067308964496961547L;

    @ApiModelProperty(value = "医生ID")
    private Long doctorId;

    @ApiModelProperty(value = "医生联系方式")
    private String telephone;

    @ApiModelProperty(value = "接通次数")
    private Integer callCount;


}
