package com.nuoxin.virtual.rep.api.web.controller.response.v2_5;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 医生基本信息医院信息动态字段包括字段填充的值
 * @author tiancun
 * @date 2018-09-17
 */
@ApiModel(description = "医生基本信息医院信息动态字段包括字段填充的值")
@Data
public class DoctorBasicDynamicFieldValueResponseBean implements Serializable {
    private static final long serialVersionUID = -6604118485650579054L;

    @ApiModelProperty(value = "这条数据的ID")
    private Long id;

    @ApiModelProperty(value = "动态字段ID")
    private Long dynamicFieldId;

    @ApiModelProperty(value = "动态字段的名称")
    private String dynamicFieldName;

    @ApiModelProperty(value = "如果是下拉框类型字段可供选择的值")
    private String dynamicFieldSelectValue;

    @ApiModelProperty(value = "字段输入的值")
    private String dynamicFieldValue;

    @ApiModelProperty(value = "字段输入的扩展值")
    private String dynamicExtendValue;

    @ApiModelProperty(value = "是否必填，1是必填，0是非必填")
    private Integer required;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，4分析，5是医院信息")
    private Integer classification;

    @ApiModelProperty(value = "字段类型，1是输入框，2单选框，3单选下拉，4联系方式，5多选下拉，6数字")
    private Integer type;


}
