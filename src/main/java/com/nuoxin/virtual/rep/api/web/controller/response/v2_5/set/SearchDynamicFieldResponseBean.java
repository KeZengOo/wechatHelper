package com.nuoxin.virtual.rep.api.web.controller.response.v2_5.set;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 搜索要用到的动态字段
 * @author tiancun
 * @date 2019-01-30
 */
@Data
@ApiModel(value = "搜索要用到的动态字段")
public class SearchDynamicFieldResponseBean implements Serializable {
    private static final long serialVersionUID = 3196945805944090046L;

    @ApiModelProperty(value = "动态字段ID")
    private Long fieldId;

    @ApiModelProperty(value = "动态字段名称")
    private String fieldName;

    @ApiModelProperty(value = "字段类型，1是输入框，2单选框，3单选下拉，4联系方式，5多选下拉，6数字")
    private Integer type;

    @ApiModelProperty(value = "扩展类型，1是潜力，2是分型")
    private Integer extendType;

    @ApiModelProperty(value = "可以选择的值，多个值以英文逗号分开")
    private String fieldValue;

    @ApiModelProperty(value = "分类，目前1基本信息，2医生的处方信息，3之前拜访记录，5是医院信息")
    private Integer classification;

    @ApiModelProperty(value = "是否是必填的，1是必填，0是非必填")
    private Integer required;

}
