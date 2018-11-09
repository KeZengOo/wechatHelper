package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author tiancun
 * @date 2018-11-09
 */
@Data
@ApiModel(value = "V2.5返回医生基本信息")
public class DoctorDetailsResponseBean implements Serializable {
    private static final long serialVersionUID = 2791020540551843667L;
    @ApiModelProperty(value = "医生ID")
    private Long doctorId;
    @ApiModelProperty(value = "姓名")
    private String doctorName;
    @ApiModelProperty(value = "医院id")
    private Long hospitalId;
    @ApiModelProperty(value = "医院名称")
    private String hospitalName;
    @ApiModelProperty(value = "医院等级")
    private String hospitalLevel;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "科室")
    private String department;


}
