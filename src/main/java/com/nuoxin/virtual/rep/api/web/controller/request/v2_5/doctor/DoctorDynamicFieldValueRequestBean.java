package com.nuoxin.virtual.rep.api.web.controller.request.v2_5.doctor;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 医生动态字段基本信息填充的值
 * @author tiancun
 * @date 2018-09-17
 */
@Data
@ApiModel(description = "医生动态字段基本信息填充的值")
public class DoctorDynamicFieldValueRequestBean implements Serializable {

    private static final long serialVersionUID = -2650826611092449657L;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息")
    private Integer classification;

    @ApiModelProperty(value = "动态字段ID")
    private Long dynamicFieldId;

    @ApiModelProperty(value = "动态字段的名称")
    private String dynamicFieldName;

    @ApiModelProperty(value = "字段输入的值")
    private String dynamicFieldValue;

    @ApiModelProperty(value = "字段输入的扩展值")
    private String dynamicExtendValue;



}
