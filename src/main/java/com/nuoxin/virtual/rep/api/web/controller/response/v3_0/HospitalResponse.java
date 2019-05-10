package com.nuoxin.virtual.rep.api.web.controller.response.v3_0;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医院返回数据
 * @author tiancun
 * @date 2019-05-06
 */
@Data
@ApiModel(value = "医院返回数据")
public class HospitalResponse implements Serializable {

    private static final long serialVersionUID = -6052008175775926180L;

    @ApiModelProperty(value = "医院ID")
    private Long hospitalId;

    @ApiModelProperty(value = "医院名称")
    private String hospitalName;

    @ApiModelProperty(value = "医院对应省份")
    private String province;

    @ApiModelProperty(value = "医院对应城市")
    private String city;

    @ApiModelProperty(value = "医院对应级别")
    private String hospitalLevel;



}
