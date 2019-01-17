package com.nuoxin.virtual.rep.api.web.controller.response.customer;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 客户设置或者产品设置返回的字段
 * @author tiancun
 * @date 2018-09-12
 */
@ApiModel(description = "客户设置或者产品设置返回的字段")
@Data
public class DoctorDynamicFieldResponseBean implements Serializable{
    private static final long serialVersionUID = 426766817698746896L;

    @ApiModelProperty(value = "id")
    private Long id;

    @ApiModelProperty(value = "序号", hidden = true)
    private Integer serialNumber;

    @ApiModelProperty(value = "字段名称")
    private String name;

    @ApiModelProperty(value = "字段类型，1是文本,2是下拉框")
    private Integer type;

    @ApiModelProperty(value = "下拉框的值")
    private String value;

    @ApiModelProperty(value = "是否必填，1是必填0是非必填")
    private Integer required;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息")
    private Integer classification;

}
