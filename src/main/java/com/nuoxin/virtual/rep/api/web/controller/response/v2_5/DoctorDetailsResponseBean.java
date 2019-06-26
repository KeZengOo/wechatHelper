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

    @ApiModelProperty(value = "医生性别")
    private Integer sex;

    @ApiModelProperty(value = "医生微信")
    private String wechat;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "医生邮箱")
    private String email;

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

    @Deprecated
    @ApiModelProperty(value = "医生职称, 字段废弃，使用新的字段")
    private String positions;

    @ApiModelProperty(value = "新的医生职称")
    private String doctorTitle;

    @ApiModelProperty(value = "新的医生职务")
    private String doctorPosition;


    @ApiModelProperty(value = "是否添加微信，1是添加，0是未添加")
    private Integer addWechat;


}
